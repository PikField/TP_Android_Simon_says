package com.pikifeld.SimonsSays;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pikifeld.SimonsSays.Entity.ButtonTP;
import com.pikifeld.SimonsSays.Entity.Mode;
import com.pikifeld.SimonsSays.Entity.SQLite;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class jeu extends AppCompatActivity  {


    static final int TIMER_ENTRE_ECLAIRAGE = 500;
    static final int TIMER_ENTRE_LEVEL = 2000;

    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10;
    Mode modeActuel;
    int levelActuel;

    ArrayList<ButtonTP> buttons;
    ArrayList<Integer> idBoutton;

    int vie;
    ArrayList<Integer> bouttonACliquer;
    ArrayList<Integer> boutonCliquerUser;

    SQLite datasource;

    String pseudo;
    float score;
    CountDownTimer timer;

    int tempRestant;
    MediaPlayer mp;

    boolean estEnJeu = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level1);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            modeActuel = bundle.getParcelable("mode");
            if(modeActuel == null)
                modeActuel = Mode.Facile;

            levelActuel = bundle.getInt("level");
            if(levelActuel == 0)
                levelActuel = 1;

            pseudo = bundle.getString("pseudo");
            if(pseudo == null){
                finish();
                Toast.makeText(this, getResources().getText(R.string.Erreur), Toast.LENGTH_SHORT).show();
            }
        }else{
            finish();
            Toast.makeText(this, getResources().getText(R.string.Erreur), Toast.LENGTH_SHORT).show();
        }


        mp = (MediaPlayer) MediaPlayer.create(this,R.raw.sound);
        mp.setLooping(false);


        datasource = new SQLite(this);

        bouttonACliquer = new ArrayList<Integer>();
        vie = modeActuel.getVie();

        boutonCliquerUser = new ArrayList<>();
        bouttonACliquer = new ArrayList<>();

        chargerLevel(levelActuel);

        score = (float)(modeActuel.getPoid()*(levelActuel-1));
        ((TextView) findViewById(R.id.textView)).setText(getResources().getText(R.string.mdoe)+": "+modeActuel.getNomMode()
                +"\n"+getResources().getText(R.string.level)+": "+levelActuel
                +"\n"+ getResources().getText(R.string.score)+": "+ score);
    }

    @Override
    protected void onStart() {
        super.onStart();
        estEnJeu =true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveLevel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveLevel();
    }

    private void saveLevel(){
        if(vie>0) {
            datasource.saveLevel(pseudo, levelActuel);
            datasource.saveMode(pseudo, modeActuel);
        }

        if(modeActuel.getNomMode().equals(Mode.Chrono))
            timer.cancel();

        mp.reset();

        System.out.println(" ---- level sauvegarder pour "+pseudo+" ------- "+datasource.getLastLevel(pseudo));
        estEnJeu = false;
        finish();
    }

    public void clickBoutton(final int bouttonClicker) {
        // Toast.makeText(this, "bouton cliquer = " + bouttonClicker+" -- "+boutonCliquerUser.size()+"----"+bouttonACliquer.size(), Toast.LENGTH_SHORT).show();


        //ToneGenerator toneGenerator = new ToneGenerator(6,100);
        //toneGenerator.startTone(buttons.get(bouttonClicker).getTone(),200);

        mp.start();

        boutonCliquerUser.add(bouttonClicker);


        if (verifierAIdemDebutB(boutonCliquerUser, bouttonACliquer) && boutonCliquerUser.size() == bouttonACliquer.size()) {
            boutonCliquerUser.clear();

            if(modeActuel.getNomMode().equals( Mode.Chrono.getNomMode()))
                timer.cancel();

            score = (float)(modeActuel.getPoid()*((levelActuel-1)*(modeActuel.getBlocMax()-modeActuel.getBlocMin())) + modeActuel.getPoid()* (bouttonACliquer.size()-modeActuel.getBlocMin()+1));



            try {
                blockSuivant();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!modeActuel.getNomMode().equals(Mode.Chrono.getNomMode()))
                ((TextView) findViewById(R.id.textView)).setText(getResources().getText(R.string.mdoe)+": "+modeActuel.getNomMode()
                        +"\n"+getResources().getText(R.string.level)+": "+levelActuel
                        +"\n"+ getResources().getText(R.string.score)+": "+ score);
            else{
                tempRestant = bouttonACliquer.size()*2000;

                String temps = String.format("%02d s", TimeUnit.MILLISECONDS.toSeconds(tempRestant));
                ((TextView) findViewById(R.id.textView)).setText(getResources().getText(R.string.mdoe) + ": " + modeActuel.getNomMode()
                        + "\n" + getResources().getText(R.string.level) + ": " + levelActuel
                        + "\n" + getResources().getText(R.string.score) + ": " + score
                        + "\n"+getResources().getText(R.string.temps_restant)+": " + temps);
            }

        }else{
            if(!verifierAIdemDebutB(boutonCliquerUser, bouttonACliquer)){
                //chargerLevel(levelActuel);
                if(modeActuel.getNomMode().equals(Mode.Chrono.getNomMode()))
                    timer.cancel();

                boutonCliquerUser.clear();
                perdreUneVie();

                blockButtons();
                if(vie>0)
                    allumerLumiere(0);
            }
        }

        setVisualVie();

    }

    private boolean verifierAIdemDebutB(ArrayList<Integer> A, ArrayList<Integer> B){

        int size=0;
        if(A.size() > B.size())
            size = B.size();
        else
            size = A.size();

        for(int i=0;i<size;i++){
            if(A.get(i) != B.get(i))
                return false;
        }
        return true;
    }

    public void blockSuivant() throws InterruptedException {
        boutonCliquerUser.clear();

        blockButtons();

        if (bouttonACliquer.size() <= modeActuel.getBlocMax()) {
            ajouterUnBlock();
            allumerLumiere(0);
        } else {
            bouttonACliquer.clear();
            levelActuel++;
            System.out.println(levelActuel);
            if(levelActuel <= 7)
                chargerLevel(levelActuel);
            else
                gameOver();
        }
    }

    private void timerChronos(){

        tempRestant = bouttonACliquer.size()*2000;

        String temps = String.format("%02d s", TimeUnit.MILLISECONDS.toSeconds(tempRestant));
        ((TextView) findViewById(R.id.textView)).setText(getResources().getText(R.string.mdoe) + ": " + modeActuel.getNomMode()
                + "\n" + getResources().getText(R.string.level) + ": " + levelActuel
                + "\n" + getResources().getText(R.string.score) + ": " + score
                + "\n"+getResources().getText(R.string.temps_restant)+": " + temps);

        timer = new CountDownTimer(tempRestant, 50) {

            public void onTick(long millisUntilFinished) {
                System.out.println("passage chrono");
                String temps = String.format("%02d s", TimeUnit.MILLISECONDS.toSeconds(tempRestant));

                if(modeActuel.getNomMode().equals(Mode.Chrono.getNomMode())) {
                    tempRestant -= 50;
                    ((TextView) findViewById(R.id.textView)).setText(getResources().getText(R.string.mdoe) + ": " + modeActuel.getNomMode()
                            + "\n" + getResources().getText(R.string.level) + ": " + levelActuel
                            + "\n" + getResources().getText(R.string.score) + ": " + score
                            + "\n"+getResources().getText(R.string.temps_restant)+": " + temps);
                }
            }

            public void onFinish() {

                String temps = String.format("%02d s", TimeUnit.MILLISECONDS.toSeconds(bouttonACliquer.size()*2000));

                if(modeActuel.getNomMode().equals(Mode.Chrono.getNomMode())) {
                    tempRestant -= 50;
                    ((TextView) findViewById(R.id.textView)).setText(getResources().getText(R.string.mdoe) + ": " + modeActuel.getNomMode()
                            + "\n" + getResources().getText(R.string.level) + ": " + levelActuel
                            + "\n" + getResources().getText(R.string.score) + ": " + score
                            + "\n"+getResources().getText(R.string.temps_restant)+": " + temps);
                }
                perdreUneVie();
                if(vie>=1){
                    allumerLumiere(0);
                }
            }
        };

        timer.start();
    }


    private void perdreUneVie(){

        vie--;
        setVisualVie();
        if(vie<=0){
            gameOver();
        }

        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 26)
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        else
            vibrator.vibrate(200);

    }

    private void allumerLumiere(final int num){

//        final ToneGenerator toneGenerator = new ToneGenerator(6,100);


        new CountDownTimer(TIMER_ENTRE_ECLAIRAGE/2, 10) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

                mp.start();
                //toneGenerator.startTone(buttons.get(num).getTone());
                new CountDownTimer(TIMER_ENTRE_ECLAIRAGE, 10) {

                    public void onTick(long millisUntilFinished) {
                        buttons.get(bouttonACliquer.get(num)).buttonLight(jeu.this);

                    }

                    public void onFinish() {
                        buttons.get(bouttonACliquer.get(num)).buttonDark(jeu.this);
                        int i = num + 1 ;
                        //toneGenerator.stopTone();
                        if(i<bouttonACliquer.size())
                            allumerLumiere(i);
                        else{
                            unBlockButons();
                            if(modeActuel.getNomMode().equals( Mode.Chrono.getNomMode()) && estEnJeu)
                                timerChronos();
                        }
                    }
                }.start();
            }
        }.start();
    }

    private void ajouterUnBlock(){

        int randomNum = (int) (Math.random() * ( levelActuel+3 - 0 ));
        bouttonACliquer.add(randomNum);
    }

    private void chargerLevel(int level){
        bouttonACliquer.clear();

        vie = modeActuel.getVie();

        if(vie>0) {
            switch (level) {
                case 1:
                    setContentView(R.layout.level1);
                    chargerButton(4);
                    levelActuel = 1;
                    break;
                case 2:
                    setContentView(R.layout.level2);
                    chargerButton(5);
                    levelActuel = 2;
                    break;
                case 3:
                    setContentView(R.layout.level3);
                    chargerButton(6);
                    levelActuel = 3;
                    break;
                case 4:
                    setContentView(R.layout.level4);
                    chargerButton(7);
                    levelActuel = 4;
                    break;
                case 5:
                    setContentView(R.layout.level5);
                    chargerButton(8);
                    levelActuel = 5;
                    break;
                case 6:
                    setContentView(R.layout.level6);
                    chargerButton(9);
                    levelActuel = 6;
                    break;
                case 7:
                    setContentView(R.layout.level7);
                    chargerButton(10);
                    levelActuel = 7;
                    break;

                default:
                    setContentView(R.layout.level2);
                    chargerButton(4);
                    levelActuel = 1;
                    break;

            }

            score = (float)(modeActuel.getPoid()*((levelActuel-1)*modeActuel.getBlocMax()));

            ((TextView) findViewById(R.id.textView)).setText(getResources().getText(R.string.mdoe)+": "+modeActuel.getNomMode()
                    +"\n"+getResources().getText(R.string.level)+": "+levelActuel
                    +"\n"+ getResources().getText(R.string.score)+": "+ score);

            for(int i=0;i<modeActuel.getBlocMin()-1;i++){
                ajouterUnBlock();
            }
            new CountDownTimer(TIMER_ENTRE_LEVEL, 10) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    try {
                        blockSuivant();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();



        }else{
            gameOver();
        }

        setVisualVie();
    }

    private void gameOver(){

        datasource.saveBestScore(pseudo,score);

        datasource.saveLevel(pseudo,0);

        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 26)
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        else
            vibrator.vibrate(200);

        new AlertDialog.Builder(this)
                .setTitle(getResources().getText(R.string.game_over))
                .setMessage(getResources().getText(R.string.you_loose)+"\n"+getResources().getText(R.string.score)+": "+score+" \n ")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(getResources().getText(R.string.voirScore), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(jeu.this,ScoreBoard.class);
                        intent.putExtra("pseudo",pseudo);
                        intent.putExtra("mode",modeActuel);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNeutralButton(getResources().getText(R.string.recommencer), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        intent.putExtra("pseudo",pseudo);
                        startActivity(intent);
                        finish();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(getResources().getText(R.string.quitter), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.star_big_on)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                })
                .show();


    }

    private void blockButtons(){
        for(int i=0;i<levelActuel+3;i++){
            buttons.get(i).button.setClickable(false);
            buttons.get(i).button.setEnabled(false);
        }
    }

    private void unBlockButons(){
        for(int i=0;i<levelActuel+3;i++){
            buttons.get(i).button.setClickable(true);
            buttons.get(i).button.setEnabled(true);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void chargerButton(int nbBouttonACharger){

        buttons = getButtons();
        idBoutton = getIdBoutton();
        ArrayList<Integer> lightColors = getLightColors();
        ArrayList<Integer> darkColors = getDarkColors();

        for(int i=0;i<nbBouttonACharger;i++){
            buttons.get(i).button = (Button)findViewById(idBoutton.get(i));
            buttons.get(i).setColorDark(darkColors.get(i));
            buttons.get(i).setColorLight(lightColors.get(i));
            buttons.get(i).setTone(i);
            final int finalI = i;
            System.out.println(buttons.get(i).button.getId());
            buttons.get(i).button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickBoutton(finalI);
                }
            });

            buttons.get(i).button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        buttons.get(finalI).buttonLight(jeu.this);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        buttons.get(finalI).buttonDark(jeu.this);
                        clickBoutton(finalI);
                    }
                    return true;
                }
            });
        }


    }

    private void setVisualVie(){

        switch (vie){
            case 1: findViewById(R.id.imageView4).setVisibility(View.VISIBLE);
                findViewById(R.id.imageView5).setVisibility(View.INVISIBLE);
                findViewById(R.id.imageView6).setVisibility(View.INVISIBLE);
                break;
            case 2: findViewById(R.id.imageView4).setVisibility(View.VISIBLE);
                findViewById(R.id.imageView5).setVisibility(View.VISIBLE);
                findViewById(R.id.imageView6).setVisibility(View.INVISIBLE);
                break;
            case 3: findViewById(R.id.imageView4).setVisibility(View.VISIBLE);
                findViewById(R.id.imageView5).setVisibility(View.VISIBLE);
                findViewById(R.id.imageView6).setVisibility(View.VISIBLE);
                break;
            default:
                findViewById(R.id.imageView4).setVisibility(View.INVISIBLE);
                findViewById(R.id.imageView5).setVisibility(View.INVISIBLE);
                findViewById(R.id.imageView6).setVisibility(View.INVISIBLE);
                break;
        }
    }

    private ArrayList<Integer> getDarkColors(){
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(R.color.boutton1);
        colors.add(R.color.boutton2);
        colors.add(R.color.boutton3);
        colors.add(R.color.boutton4);
        colors.add(R.color.boutton5);
        colors.add(R.color.boutton6);
        colors.add(R.color.boutton7);
        colors.add(R.color.boutton8);
        colors.add(R.color.boutton9);
        colors.add(R.color.boutton10);
        return colors;
    }

    private ArrayList<Integer> getLightColors(){
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(R.color.Boutton1Allume);
        colors.add(R.color.Boutton2Allume);
        colors.add(R.color.Boutton3Allume);
        colors.add(R.color.Boutton4Allume);
        colors.add(R.color.Boutton5Allume);
        colors.add(R.color.Boutton6Allume);
        colors.add(R.color.Boutton7Allume);
        colors.add(R.color.Boutton8Allume);
        colors.add(R.color.Boutton9Allume);
        colors.add(R.color.Boutton10Allume);
        return  colors;
    }

    private ArrayList<ButtonTP> getButtons(){
        ArrayList<ButtonTP> buttons = new ArrayList<>();
        buttons.add(new ButtonTP(button1));
        buttons.add(new ButtonTP(button2));
        buttons.add(new ButtonTP(button3));
        buttons.add(new ButtonTP(button4));
        buttons.add(new ButtonTP(button5));
        buttons.add(new ButtonTP(button6));
        buttons.add(new ButtonTP(button7));
        buttons.add(new ButtonTP(button8));
        buttons.add(new ButtonTP(button9));
        buttons.add(new ButtonTP(button10));

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
