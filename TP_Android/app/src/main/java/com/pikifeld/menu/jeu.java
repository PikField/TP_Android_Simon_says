package com.pikifeld.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pikifeld.menu.Entity.Mode;

import java.util.ArrayList;
import java.util.Random;

public class jeu extends AppCompatActivity  {


    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10;
    Mode modeActuel;

    ArrayList<Button> buttons;
    ArrayList<Integer> idBoutton;

    ArrayList<Color> couleurs;

    int vie;


    ArrayList<Integer> bouttonACliquer;
    int nbBoutonAcliquer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level1);

        bouttonACliquer = new ArrayList<Integer>();
        modeActuel = Mode.Facile;
        nbBoutonAcliquer = Mode.Facile.getBlocMin();
        vie = Mode.Facile.getVie();


        chargerLevel(2);



    }


    public void clickBoutton(int bouttonClicker){
        Toast.makeText(this, "bouton cliquer = "+bouttonClicker, Toast.LENGTH_SHORT).show();

    }

    public void blockSuivant(){
        ajouterUnBlock();
    }

    private void ajouterUnBlock(){
        Random rand = null;
        int randomNum = rand.nextInt((modeActuel.getBlocMin() - modeActuel.getBlocMax()) + 1) + modeActuel.getBlocMax();
        bouttonACliquer.add(randomNum);
    }

    private void chargerLevel(int level){
        switch (level){
            case 1:
                setContentView(R.layout.level1);
                chargerButton(4);
                break;
            case 2:
                setContentView(R.layout.level2);
                chargerButton(5);
                break;
            case 3:
                setContentView(R.layout.level3);
                chargerButton(6);
                break;
            case 4:
                setContentView(R.layout.level4);
                chargerButton(7);
                break;
            case 5:
                chargerButton(8);
                break;
            case 6:
                chargerButton(9);
                break;
            case 7:
                chargerButton(10);
                break;

            default:
                setContentView(R.layout.level1);
                chargerButton(4);
                break;

        }
    }

    private void chargerButton(int nbBouttonACharger){

        buttons = getButtons();
        idBoutton = getIdBoutton();


        for(int i=0;i<nbBouttonACharger;i++){
            buttons.set(i,(Button)findViewById(idBoutton.get(i)));
            final int finalI = i;
            buttons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickBoutton(finalI);
                }
            });


        }
    }

    private ArrayList<Button> getButtons(){
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
        buttons.add(button8);
        buttons.add(button9);
        buttons.add(button10);

        return buttons;
    }

    private ArrayList<Integer> getIdBoutton(){
        ArrayList<Integer> ids = new ArrayList<>();

        ids.add(R.id.button1);
        ids.add(R.id.button2);
        ids.add(R.id.button3);
        ids.add(R.id.button4);
        ids.add(R.id.button5);
        ids.add(R.id.button6);
        ids.add(R.id.button7);
        ids.add(R.id.button8);
        ids.add(R.id.button9);
        ids.add(R.id.button10);

        return ids;
    }


}
