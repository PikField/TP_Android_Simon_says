package com.pikifeld.SimonsSays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.pikifeld.SimonsSays.Entity.Mode;
import com.pikifeld.SimonsSays.Entity.SQLite;
import com.pikifeld.SimonsSays.Entity.User;
import com.pikifeld.SimonsSays.adapter.ScoreAdapter;

import java.util.ArrayList;

public class ScoreBoard extends AppCompatActivity {

    SQLite database;
    Mode modeActuel;
    String pseudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        database = new SQLite(this);


        ArrayList<User> users = database.getAllScores();

        ListView listView = (ListView)findViewById(R.id.listScore);

        ScoreAdapter adapter = new ScoreAdapter(this,users);
        listView.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            modeActuel = bundle.getParcelable("mode");
            if (modeActuel == null)
                findViewById(R.id.Recommencer).setVisibility(View.GONE);

            pseudo = bundle.getString("pseudo");

        }

        findViewById(R.id.Quitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreBoard.this, MenuPrincipale.class);
                intent.putExtra("pseudo",pseudo);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.Recommencer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jeu = new Intent(ScoreBoard.this, jeu.class);
                jeu.putExtra("pseudo", pseudo);
                jeu.putExtra("level", 1);
                jeu.putExtra("mode", modeActuel);
                startActivity(jeu);
                finish();
            }
        });

    }
}
