package com.pikifeld.SimonsSays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pikifeld.SimonsSays.Entity.Mode;
import com.pikifeld.SimonsSays.Entity.SQLite;
import com.pikifeld.SimonsSays.Entity.User;

public class MenuPrincipale extends AppCompatActivity {


    String pseudo;
    SQLite datasource;
    User user;


    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principale);


        update();

    }

    private void update(){
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("utilisateur");
        myRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                user = snapshot.getValue(User.class);

                ((TextView) findViewById(R.id.textBienvenue)).setText(getResources().getText(R.string.Bienvenue)+" "+user.getPseudo());

                if(user.getLastLevel()==0)
                    findViewById(R.id.reprendreSave).setVisibility(View.GONE);


                ((Button) findViewById(R.id.modeFacile)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent jeu = new Intent(MenuPrincipale.this, jeu.class);
                        jeu.putExtra("user", user);
                        jeu.putExtra("level",1);
                        jeu.putExtra("mode", Mode.Facile);
                        startActivity(jeu);
                    }
                });
                ((Button) findViewById(R.id.modeDifficile)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent jeu = new Intent(MenuPrincipale.this, jeu.class);
                        jeu.putExtra("user", user);
                        jeu.putExtra("level",1);
                        jeu.putExtra("mode", Mode.Difficile);
                        startActivity(jeu);
                    }
                });
                ((Button) findViewById(R.id.modeExpert)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent jeu = new Intent(MenuPrincipale.this, jeu.class);
                        jeu.putExtra("user", user);
                        jeu.putExtra("level",1);
                        jeu.putExtra("mode", Mode.Expert);
                        startActivity(jeu);
                    }
                });
                ((Button) findViewById(R.id.modeChronos)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent jeu = new Intent(MenuPrincipale.this, jeu.class);
                        jeu.putExtra("user", user);
                        jeu.putExtra("level",1);
                        jeu.putExtra("mode", Mode.Chrono);
                        startActivity(jeu);
                    }
                });
                ((Button) findViewById(R.id.reprendreSave)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent jeu = new Intent(MenuPrincipale.this, jeu.class);
                        jeu.putExtra("user", user);
                        jeu.putExtra("level",user.getLastLevel());

                        if(user.getLastMode().equals(Mode.Facile.getNomMode()))
                            jeu.putExtra("mode",Mode.Facile );
                        else if(user.getLastMode().equals(Mode.Difficile.getNomMode()))
                            jeu.putExtra("mode",Mode.Difficile);
                        else if(user.getLastMode().equals(Mode.Expert.getNomMode()))
                            jeu.putExtra("mode",Mode.Expert );
                        else if(user.getLastMode().equals(Mode.Chrono.getNomMode()))
                            jeu.putExtra("mode",Mode.Chrono );

                        startActivity(jeu);
                    }
                });

                ((Button) findViewById(R.id.voirScores)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent scoreBoard = new Intent(MenuPrincipale.this, ScoreBoard.class);
                        scoreBoard.putExtra("user", user);
                        startActivity(scoreBoard);
                    }
                });

                ((Button) findViewById(R.id.Profil)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent scoreBoard = new Intent(MenuPrincipale.this, Profil.class);
                        startActivity(scoreBoard);
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        update();
    }
}
