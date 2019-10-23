package com.pikifeld.SimonsSays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pikifeld.SimonsSays.Entity.SQLite;
import com.pikifeld.SimonsSays.Entity.User;

import java.util.ArrayList;

public class Profil extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


        EditText textNom, textPrenom, textPseudo, textMail, textMdp, textMdpBis;
        Button sendButton;
        Spinner sexeSpin, ageSpin;
        User user;


        SQLite data = new SQLite(this);


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);



            setContentView(R.layout.inscription);
            String pseudo = getIntent().getExtras().getString("pseudo");
            sendButton = findViewById(R.id.button11);
            textNom= findViewById(R.id.editText3);
            textPrenom= findViewById(R.id.editText4);
            textPseudo= findViewById(R.id.editText6);
            textMail= findViewById(R.id.editText5);
            textMdp= findViewById(R.id.editText7);
            textMdpBis= findViewById(R.id.editText8);
            ageSpin=findViewById(R.id.spinner2);
            sexeSpin=findViewById(R.id.spinner);

            user = data.getProfile(pseudo);

            textNom.setText(user.getNom());
            textPrenom.setText(user.getPrenom());
            textPseudo.setText(user.getPseudo());
            textMail.setText(user.getMail());
            textPseudo.setEnabled(false);

            ageSpin.setSelection(user.getAge());
System.out.println("++++++++++++++++++++++++"+user.getSexe()+"eeeeeeeee"+user.getAge());
            switch (user.getSexe()){
                case "Femme":
                    sexeSpin.setSelection(1);
                    break;
                case "Homme":
                    sexeSpin.setSelection(0);
                    break;
                default:
                    sexeSpin.setSelection(2);
                    break;²
            }

            ArrayList<String> listAge = new ArrayList<>();
            for(int k=0; k<100;k++){
                listAge.add(String.valueOf(k));
            }

            ArrayAdapter<String> ageAdapt = new ArrayAdapter<String>(this, R.layout.item_spinner, listAge);

            //simple_spinner_dropdown_item
            ageSpin.setAdapter(ageAdapt);

            ageAdapt.setDropDownViewResource(R.layout.dropdown_spinner);
            ageSpin.setAdapter(ageAdapt);
            ageSpin.setOnItemSelectedListener(this);



            ArrayAdapter sexeAdapt = ArrayAdapter.createFromResource(
                    this,
                    R.array.sexe,
                    R.layout.item_spinner);
            sexeAdapt.setDropDownViewResource(R.layout.dropdown_spinner);
            sexeSpin.setAdapter(sexeAdapt);
            sexeSpin.setOnItemSelectedListener(this);


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
                            if(name.equals("") || surname.equals("") || pseudo.equals("") || mail.equals("") ){
                                Toast.makeText(getApplicationContext(),"Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                            }else{
                                if(mdp.equals(mdpBis)) {


                                    long value = data.changeProfile(name, surname, age, sexe, pseudo, mail, mdp);
                                    if (value==-1){
                                        Toast.makeText(getApplicationContext(),"Le pseudo choisit existe déjà, veuiller en utiliser un autre", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Modifications réussis", Toast.LENGTH_SHORT).show();

                                        Intent Menu = new Intent(com.pikifeld.SimonsSays.Profil.this, MenuPrincipale.class);
                                        Menu.putExtra("pseudo",pseudo);
                                        startActivity(Menu);
                                    }

                                }else{
                                    Toast.makeText(getApplicationContext(),"Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                                }
                            }





                        }

                    }
            );



        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            // Toast.makeText(this, adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


