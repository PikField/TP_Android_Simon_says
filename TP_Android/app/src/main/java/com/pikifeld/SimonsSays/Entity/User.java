package com.pikifeld.SimonsSays.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    String nom,prenom,sexe,mail,pseudo;
    float bestscore;
    int age;
    int lastLevel;
    String lastMode;

    public User(){}

    public User(String pseudo,float bestscore){
        this.pseudo = pseudo;
        this.bestscore = bestscore;
    }

    public User(String nom,String prenom, String sexe, String pseudo, float bestscore, int age){
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.pseudo = pseudo;
        this.bestscore = bestscore;
        this.age = age;
    }
    public User(String nom,String prenom, String sexe, String pseudo, float bestscore, int age,int lastLevel){
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.pseudo = pseudo;
        this.bestscore = bestscore;
        this.age = age;
        this.lastLevel = lastLevel;
    }
    public User(String nom,String prenom, String sexe, String pseudo, String mail, float bestscore, int age){
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.mail=mail;
        this.pseudo = pseudo;
        this.bestscore = bestscore;
        this.age = age;

    }

    public float getBestscore() {
        return bestscore;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public String getMail() {
        return mail;
    }

    public String getSexe() {
        return sexe;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public String getLastMode() {
        return lastMode;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBestscore(float bestscore) {
        this.bestscore = bestscore;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setLastLevel(int lastLevel) {
        this.lastLevel = lastLevel;
    }

    public void setLastMode(String lastMode) {
        this.lastMode = lastMode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeInt(age);
        dest.writeString(pseudo);
        dest.writeString(mail);
        dest.writeString(sexe);
        dest.writeFloat(bestscore);
        dest.writeInt(lastLevel);
        dest.writeString(lastMode);
    }

    protected User(Parcel in) {
        nom = in.readString();
        prenom = in.readString();
        age = in.readInt();
        pseudo = in.readString();
        mail = in.readString();
        sexe = in.readString();
        bestscore = in.readFloat();
        lastLevel = in.readInt();
        lastMode = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
