package com.pikifeld.menu;

import android.content.Intent;
import android.icu.text.Normalizer2;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pikifeld.menu.Entity.Mode;
import com.pikifeld.menu.Entity.SQLite;

public class connexion extends AppCompatActivity {
    String pseudo;
    String mdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SQLite data = new SQLite(this);
        setContentView(R.layout.connexion);

        final Button buttonConnexion = findViewById(R.id.connexion);
        final Button buttonInscription = findViewById(R.id.button);

        final EditText textPseudo = findViewById(R.id.editText);
        final EditText textMDP = findViewById(R.id.editText2);
        final Toast toast;
        buttonConnexion.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        pseudo= textPseudo.getText().toString();
                        mdp= textMDP.getText().toString();

                        if (pseudo=="" || mdp==""){
                            Toast.makeText(getApplicationContext(),"Veuillez rentrer les informations", Toast.LENGTH_SHORT).show();
                                                    }
                        switch ( data.autenticateUser(pseudo, mdp)){
                            case 1 :
                               Toast.makeText(getApplicationContext(),"Identifiant incorect", Toast.LENGTH_SHORT).show();
                                        break;
                            case 2 :
                                Toast.makeText(getApplicationContext(),"Mot de passe incorect", Toast.LENGTH_SHORT).show();
                                break;
                            case 0 :
                                Toast.makeText(getApplicationContext(),"Authentification réussi", Toast.LENGTH_SHORT).show();
                                Intent Menu = new Intent(connexion.this, MenuPrincipale.class);
                                Menu.putExtra("pseudo", pseudo);
                                startActivity(Menu);
                            break;

                            default :
                                Toast.makeText(getApplicationContext(),"Erreur", Toast.LENGTH_SHORT).show();
                                break;
                        }


                    }
                }
        );

        buttonInscription.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent inscription = new Intent(connexion.this, inscription.class);
                        startActivity(inscription);
                    }
                }
        );
    }

}
