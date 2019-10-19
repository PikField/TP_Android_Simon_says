package com.pikifeld.menu.Entity;

public class User {

    String nom,prenom,sexe,mail,pseudo;
    float bestscore;
    int age;

    public User(String pseudo,float bestscore){
        this.pseudo = pseudo;
        this.bestscore = bestscore;
    }

    public float getBestscore() {
        return bestscore;
    }

    public String getPseudo() {
        return pseudo;
    }
}
