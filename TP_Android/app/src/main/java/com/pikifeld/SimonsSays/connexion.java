package com.pikifeld.SimonsSays;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pikifeld.SimonsSays.Entity.SQLite;

import java.util.Arrays;
import java.util.List;

public class connexion extends AppCompatActivity {
    String pseudo;
    String mdp;

    private static final int RC_SIGN_IN = 123;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        final SQLite data = new SQLite(this);

        auth = FirebaseAuth.getInstance();

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
                        }else {

                        /*
                        switch ( data.autenticateUser(user, mdp)){
                            case 1 :
                               Toast.makeText(getApplicationContext(),"Identifiant incorect", Toast.LENGTH_SHORT).show();
                                        break;
                            case 2 :
                                Toast.makeText(getApplicationContext(),"Mot de passe incorect", Toast.LENGTH_SHORT).show();
                                break;
                            case 0 :
                                Toast.makeText(getApplicationContext(),"Authentification réussi", Toast.LENGTH_SHORT).show();
                                Intent Menu = new Intent(connexion.this, MenuPrincipale.class);
                                Menu.putExtra("user", user);
                                startActivity(Menu);
                            break;

                            default :
                                Toast.makeText(getApplicationContext(),"Erreur", Toast.LENGTH_SHORT).show();
                                break;
                        }
*/
                            auth.signInWithEmailAndPassword(pseudo, mdp)
                                    .addOnCompleteListener(connexion.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (!task.isSuccessful()) {
                                                // there was an error
                                                if (mdp.length() < 6) {
                                                    textPseudo.setError("mot de passe trop court !");
                                                } else {
                                                    Toast.makeText(connexion.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                Intent intent = new Intent(connexion.this, MenuPrincipale.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });
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
