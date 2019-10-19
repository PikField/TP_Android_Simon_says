package com.pikifeld.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pikifeld.menu.Entity.Mode;
import com.pikifeld.menu.adapter.ScoreAdapter;

public class MenuPrincipale extends AppCompatActivity {


    String pseudo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principale);

        pseudo = getIntent().getStringExtra("pseudo");

        ((TextView) findViewById(R.id.textBienvenue)).setText(getResources().getText(R.string.Bienvenue)+pseudo);

        ((Button) findViewById(R.id.modeFacile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent jeu = new Intent(MenuPrincipale.this, jeu.class);
                jeu.putExtra("pseudo", pseudo);
                jeu.putExtra("level",1);
                jeu.putExtra("mode", Mode.Facile);
                startActivity(jeu);
            }
        });
        ((Button) findViewById(R.id.modeDifficile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent jeu = new Intent(MenuPrincipale.this, jeu.class);
                jeu.putExtra("pseudo", pseudo);
                jeu.putExtra("level",1);
                jeu.putExtra("mode", Mode.Difficile);
                startActivity(jeu);
            }
        });
        ((Button) findViewById(R.id.modeExpert)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent jeu = new Intent(MenuPrincipale.this, jeu.class);
                jeu.putExtra("pseudo", pseudo);
                jeu.putExtra("level",1);
                jeu.putExtra("mode", Mode.Expert);
                startActivity(jeu);
            }
        });
        ((Button) findViewById(R.id.modeChronos)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent jeu = new Intent(MenuPrincipale.this, jeu.class);
                jeu.putExtra("pseudo", pseudo);
                jeu.putExtra("level",1);
                jeu.putExtra("mode", Mode.Chrono);
                startActivity(jeu);
            }
        });

        ((Button) findViewById(R.id.voirScores)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent scoreBoard = new Intent(MenuPrincipale.this, ScoreBoard.class);
                scoreBoard.putExtra("pseudo", pseudo);
                startActivity(scoreBoard);
            }
        });
    }
}
