package com.pikifeld.SimonsSays;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pikifeld.SimonsSays.Entity.Mode;
import com.pikifeld.SimonsSays.Entity.SQLite;
import com.pikifeld.SimonsSays.Entity.User;
import com.pikifeld.SimonsSays.adapter.ScoreAdapter;

import java.util.ArrayList;

public class ScoreBoard extends AppCompatActivity {

    SQLite database;
    Mode modeActuel;
    User user;


    FirebaseAuth auth;
    ArrayList<User> users ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        database = new SQLite(this);


       users = new ArrayList<>();//= database.getAllScores();


        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query myRef = database.getReference("utilisateur").orderByChild("bestscore");

        final ListView listView = (ListView)findViewById(R.id.listScore);

        // My top posts by number of stars
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    users.add(postSnapshot.getValue(User.class));
                }

                ScoreAdapter adapter = new ScoreAdapter(ScoreBoard.this,users);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TEST", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });




        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            modeActuel = bundle.getParcelable("mode");
            if (modeActuel == null)
                findViewById(R.id.Recommencer).setVisibility(View.GONE);

            user = bundle.getParcelable("user");

        }

        findViewById(R.id.Quitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreBoard.this, MenuPrincipale.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.Recommencer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jeu = new Intent(ScoreBoard.this, jeu.class);
                jeu.putExtra("user", user);
                jeu.putExtra("level", 1);
                jeu.putExtra("mode", modeActuel);
                startActivity(jeu);
                finish();
            }
        });

    }
}
