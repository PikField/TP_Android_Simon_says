package com.pikifeld.SimonsSays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pikifeld.SimonsSays.Entity.User;

import java.util.ArrayList;

public class Profil extends AppCompatActivity {

    User user;

    EditText textNom, textPrenom, textPseudo, textMail, textMdp, textMdpBis;
    Button sendButton;
    Spinner sexeSpin, ageSpin;
    final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    final ArrayList<String> listAge = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);


        sendButton = findViewById(R.id.button11);
        textNom= findViewById(R.id.editText3);
        textPrenom= findViewById(R.id.editText4);
        textPseudo= findViewById(R.id.editText6);
        textMail= findViewById(R.id.editText5);
        textMdp= findViewById(R.id.editText7);
        textMdpBis= findViewById(R.id.editText8);
        ageSpin=findViewById(R.id.spinner2);
        sexeSpin=findViewById(R.id.spinner);

        sendButton.setText("Enregistrer");

        textPseudo.setEnabled(false);

        for(int k=0; k<100;k++){
            listAge.add(String.valueOf(k));
        }

        ArrayAdapter<String> dataAdaptater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listAge);
        dataAdaptater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpin.setAdapter(dataAdaptater);


        ArrayAdapter<String> ageAdapt = new ArrayAdapter<String>(this, R.layout.item_spinner, listAge);

        //simple_spinner_dropdown_item
        ageSpin.setAdapter(ageAdapt);

        ageAdapt.setDropDownViewResource(R.layout.dropdown_spinner);
        ageSpin.setAdapter(ageAdapt);



        ArrayAdapter sexeAdapt = ArrayAdapter.createFromResource(
                this,
                R.array.sexe,
                R.layout.item_spinner);
        sexeAdapt.setDropDownViewResource(R.layout.dropdown_spinner);
        sexeSpin.setAdapter(sexeAdapt);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = database.getReference("utilisateur").child(mAuth.getCurrentUser().getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                updateVisual(user);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TEST", "loadPost:onCancelled", databaseError.toException());
            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User userLocal = new User(textNom.getText().toString()
                                        ,textPrenom.getText().toString()
                                        ,sexeSpin.getSelectedItem().toString()
                                        ,textPseudo.getText().toString()
                                        ,user.getBestscore()
                                        ,Integer.parseInt(ageSpin.getSelectedItem().toString())
                                        ,user.getLastLevel());


                myRef.setValue(userLocal);

                if(!mAuth.getCurrentUser().getEmail().equals(textMail.getText().toString()))
                    mAuth.getCurrentUser().updateEmail(textMail.getText().toString());

                if(textMdp.getText().toString().equals(textMdpBis.getText().toString()) && !textMdp.getText().toString().equals(""))
                    mAuth.getCurrentUser().updatePassword(textMdp.getText().toString());

                finish();
            }
        });



    }

    private void updateVisual(User user){
        textNom.setText(user.getNom());
        textPrenom.setText(user.getPrenom());
        textPseudo.setText(user.getPseudo());
        textMail.setText(mAuth.getCurrentUser().getEmail());

        System.out.println(user.getAge()+ " -- "+user.getSexe());
        ageSpin.setSelection(user.getAge());

        switch (user.getSexe()){
            case "Homme": sexeSpin.setSelection(0);
                break;
            case "Femme": sexeSpin.setSelection(1);
                break;
            default: sexeSpin.setSelection(2);
                break;
        }
    }
}
