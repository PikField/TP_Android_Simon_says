package com.pikifeld.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pikifeld.menu.Entity.SQLite;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class inscription extends AppCompatActivity {
    EditText textNom, textPrenom, textPseudo, textMail, textMdp, textMdpBis;
    Button sendButton;
    Spinner sexeSpin, ageSpin;
    SQLite data = new SQLite(this);
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
                        String name=textNom.getText().toString();
                        String surname=textPrenom.getText().toString();
                        String pseudo=textPseudo.getText().toString();
                        String  mail=textMail.getText().toString();
                        String  mdp=textMdp.getText().toString();
                        String mdpBis=textMdpBis.getText().toString();
                        String age = ageSpin.getSelectedItem().toString();
                        String sexe =sexeSpin.getSelectedItem().toString();
                        if(name.equals("") || surname.equals("") || pseudo.equals("") || mail.equals("") || mdp.equals("")){
                            Toast.makeText(getApplicationContext(),"Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                        }else{
                            if(mdp.equals(mdpBis)) {
                                long value = data.createUser(name, surname, age, sexe, pseudo, mail, mdp);
                                if (value==-1){
                                    Toast.makeText(getApplicationContext(),"Le pseudo choisit existe déjà, veuiller en utiliser un autre", Toast.LENGTH_SHORT).show();
                                 }else{
                                    Toast.makeText(getApplicationContext(),"Inscription réussi", Toast.LENGTH_SHORT).show();
                                    Intent connect = new Intent(inscription.this, connexion.class);
                                    startActivity(connect);
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                            }
                        }

                        }

                }
        );



    }
}
