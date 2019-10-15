package com.pikifeld.menu.Entity;

public class Mode {

    private int blocMin, blocMax;
    private int tempsReponse;
    private int vie;
    private double poid;
    private String nomMode;


    public Mode(int blocMin,int blocMax,int tempsReponse, int vie,double poid){
        this.blocMin = blocMin;
        this.blocMax = blocMax;
        this.tempsReponse =tempsReponse;
        this.vie = vie;
        this.poid = poid;
    }


    public Mode(int blocMin,int blocMax,int tempsReponse, int vie,double poid, String nomMode){
        this.blocMin = blocMin;
        this.blocMax = blocMax;
        this.tempsReponse =tempsReponse;
        this.vie = vie;
        this.poid = poid;
        this.nomMode = nomMode;
    }

    public static final Mode Facile = new Mode(1,8,0,2,1,"Facile");
    public static final Mode Difficile = new Mode(3,12,0,2,1.5,"Difficile");
    public static final Mode Expert = new Mode(5,15,0,3,2,"Expert");
    public static final Mode Chrono = new Mode(1,10,2,3,1.5,"Chrono");

    public double getPoid() {
        return poid;
    }

    public int getBlocMax() {
        return blocMax;
    }

    public int getBlocMin() {
        return blocMin;
    }

    public int getTempsReponse() {
        return tempsReponse;
    }

    public int getVie() {
        return vie;
    }

    public String getNomMode() {
        return nomMode;
    }
}
