package com.pikifeld.SimonsSays;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pikifeld.SimonsSays.Entity.SQLite;
import com.pikifeld.SimonsSays.Entity.User;

import java.util.ArrayList;



public class inscription extends AppCompatActivity {
    EditText textNom, textPrenom, textPseudo, textMail, textMdp, textMdpBis;
    Button sendButton;
    Spinner sexeSpin, ageSpin;
    SQLite data = new SQLite(this);

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

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


        ArrayList<String> listAge = new ArrayList<>();
        for(int k=0; k<100;k++){
            listAge.add(String.valueOf(k));
        }

        ArrayAdapter<String> dataAdaptater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listAge);
        dataAdaptater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpin.setAdapter(dataAdaptater);





        sendButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        final String name=textNom.getText().toString();
                        final String surname=textPrenom.getText().toString();
                        final String pseudo=textPseudo.getText().toString();
                        String  mail=textMail.getText().toString();
                        String  mdp=textMdp.getText().toString();
                        String mdpBis=textMdpBis.getText().toString();
                        final String age = ageSpin.getSelectedItem().toString();
                        final String sexe =sexeSpin.getSelectedItem().toString();
                        if(name.equals("") || surname.equals("") || pseudo.equals("") || mail.equals("") || mdp.equals("")){
                            Toast.makeText(getApplicationContext(),"Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                        }else{
                            if(mdp.equals(mdpBis)) {

                                mAuth.createUserWithEmailAndPassword(mail, mdp)
                                        .addOnCompleteListener(inscription.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    FirebaseUser user = mAuth.getCurrentUser();


                                                    mAuth = FirebaseAuth.getInstance();
                                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                    DatabaseReference myRef = database.getReference("utilisateur");



                                                    User userLocal = new User(name,surname,sexe,pseudo,0,Integer.parseInt(age),0);


                                                    myRef.child(user.getUid()).setValue(userLocal);

                                                    Intent intent = new Intent(inscription.this,connexion.class);
                                                    startActivity(intent);

                                                } else {
                                                    // If sign in fails, display a message to the user.
                                                    Toast.makeText(inscription.this, "Authentication failed.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

/*

                                long value = data.createUser(name, surname, age, sexe, pseudo, mail, mdp);
                                if (value==-1){
                                    Toast.makeText(getApplicationContext(),"Le pseudo choisit existe déjà, veuiller en utiliser un autre", Toast.LENGTH_SHORT).show();
                                 }else{
                                    Toast.makeText(getApplicationContext(),"Inscription réussi", Toast.LENGTH_SHORT).show();

                                    Intent connect = new Intent(inscription.this, connexion.class);
                                    startActivity(connect);
                                }
*/
                            }else{
                                Toast.makeText(getApplicationContext(),"Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                            }
                        }





                    }

                }
        );



    }

}
