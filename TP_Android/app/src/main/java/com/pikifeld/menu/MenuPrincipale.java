package com.pikifeld.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pikifeld.menu.Entity.Mode;
import com.pikifeld.menu.Entity.SQLite;
import com.pikifeld.menu.adapter.ScoreAdapter;

public class MenuPrincipale extends AppCompatActivity {


    String pseudo;
    SQLite datasource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principale);

        pseudo = getIntent().getStringExtra("pseudo");

        ((TextView) findViewById(R.id.textBienvenue)).setText(getResources().getText(R.string.Bienvenue)+" "+pseudo);

        datasource = new SQLite(this);

        if(datasource.getLastMode(pseudo) == "" || datasource.getLastLevel(pseudo) == 0)
            findViewById(R.id.reprendreSave).setVisibility(View.GONE);

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
        ((Button) findViewById(R.id.reprendreSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent jeu = new Intent(MenuPrincipale.this, jeu.class);
                jeu.putExtra("pseudo", pseudo);
                jeu.putExtra("level",datasource.getLastLevel(pseudo));

                if(datasource.getLastMode(pseudo).equals(Mode.Facile.getNomMode()))
                    jeu.putExtra("mode",Mode.Facile );
                else if(datasource.getLastMode(pseudo).equals(Mode.Difficile.getNomMode()))
                    jeu.putExtra("mode",Mode.Difficile);
                else if(datasource.getLastMode(pseudo).equals(Mode.Expert.getNomMode()))
                    jeu.putExtra("mode",Mode.Expert );
                else if(datasource.getLastMode(pseudo).equals(Mode.Chrono.getNomMode()))
                    jeu.putExtra("mode",Mode.Chrono );

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
