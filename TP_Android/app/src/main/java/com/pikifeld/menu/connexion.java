package com.pikifeld.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pikifeld.menu.Entity.SQLite;

public class connexion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SQLite data = new SQLite(this);
        setContentView(R.layout.connexion);

        final Button buttonConnexion = findViewById(R.id.connexion);
        final Button buttonInscription = findViewById(R.id.button);

        final EditText textPseudo = findViewById(R.id.editText);
        final EditText textMDP = findViewById(R.id.editText2);
        final String[] pseudo = {""};
        final String[] mdp = {""};
        final Toast toast;
        buttonConnexion.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        pseudo[0]= textPseudo.getText().toString();
                        mdp[0]= textMDP.getText().toString();

                        if (pseudo[0]=="" || mdp[0]==""){
                            Toast.makeText(getApplicationContext(),"Veuillez rentrer les informations", Toast.LENGTH_SHORT).show();
                                                    }
                        switch ( data.autenticateUser(pseudo[0], mdp[0])){
                            case 1 :
                               Toast.makeText(getApplicationContext(),"Identifiant incorect", Toast.LENGTH_SHORT).show();
                                        break;
                            case 2 :
                                Toast.makeText(getApplicationContext(),"Mot de passe incorect", Toast.LENGTH_SHORT).show();
                                break;
                            case 0 :
                                Toast.makeText(getApplicationContext(),"Authentification réussi", Toast.LENGTH_SHORT).show();
                                Intent jeu = new Intent(connexion.this, jeu.class);
                                jeu.putExtra("pseudo", pseudo);
                                startActivity(jeu);
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
