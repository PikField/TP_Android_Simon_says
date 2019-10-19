package com.pikifeld.menu.Entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {


    //information of database
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME = "Utilisateur";
    public static final String COLUMN_NOM = "nom";
    public static final String COLUMN_PRENOM = "prenom";
    public static final String COLUMN_SEXE= "sexe";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_mail = "mail";
    public static final String COLUMN_pseudo = "pseudo";
    public static final String COLUMN_mdp = "mdp";
    public static final String COLUMN_bestscore = "bestscore";
    public static final String COLUMN_last_save = "last_saved_game";
    public static final String COLUMN_last_mode_save = "last_saved_mode";

    public SQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String text="CREATE TABLE "+ TABLE_NAME + "(" + COLUMN_NOM + " Text," +  COLUMN_PRENOM+ " Text,"+COLUMN_AGE+" Text," +  COLUMN_SEXE+ " Text," +
                COLUMN_mail +  " Text," +
                COLUMN_pseudo+ " Text," +
                COLUMN_mdp +" Text,"+
                COLUMN_last_mode_save +" Text,"+
                COLUMN_last_save +" integer,"+
                COLUMN_bestscore +" Text);";
        Log.i("Tag", "........"+text);
        db.execSQL(text);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    public void onDelete() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery ="DELETE FROM " + TABLE_NAME + " WHERE pseudo=''";
        Cursor c = db.rawQuery(selectQuery, null);
    }

    public long createUser(String name, String surname, String age,String sexe, String pseudo, String mail, String mdp) {
        // Creating content values
        long insert;
        String selectQuery = "SELECT " + COLUMN_pseudo + " FROM " + TABLE_NAME+" WHERE pseudo ='"+pseudo+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.getCount()==0) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOM, name);
            values.put(COLUMN_PRENOM, surname);
            values.put(COLUMN_AGE, age);
            values.put(COLUMN_SEXE, sexe);
            values.put(COLUMN_mail, mail);
            values.put(COLUMN_pseudo, pseudo);
            values.put(COLUMN_mdp, mdp);
            values.put(COLUMN_bestscore, "0");
            values.put(COLUMN_last_save, 0);
            values.put(COLUMN_last_mode_save, "");
            insert = db.insert(TABLE_NAME, null, values);
        }else {
            insert = -1;
        }
        return insert;
    }


    public int autenticateUser(String pseudobis, String password){

        String selectQuery = "SELECT " +COLUMN_mdp+ " FROM " + TABLE_NAME+" WHERE "+ COLUMN_pseudo +" ='"+pseudobis+"'" ;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if(c.getCount()!= 1) {
            return 1;
        }else {
            c.moveToFirst();
            if (c.getString(0).equals(password)) {
                return 0;
            } else {
                return 2;
            }
        }
    }

    public int saveBestScore(String pseudo, float score){

        String selectQuery = "SELECT " +COLUMN_bestscore+ " FROM " + TABLE_NAME+" WHERE "+ COLUMN_pseudo +" = '"+pseudo+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        c.moveToFirst();
        if(Float.parseFloat(c.getString(0)) < score){
            String updateQuery =  "UPDATE "+ TABLE_NAME +" SET "+ COLUMN_bestscore+" = "+ score +" Where "+ COLUMN_pseudo + " =  '" + pseudo+"'" ;
            db.execSQL(updateQuery);
            return 1;
        }else {
            return 0;
        }
    }
    public void saveLevel(String pseudo, int level){

        String selectQuery = "UPDATE "+ TABLE_NAME +" SET "+ COLUMN_last_save+" = "+ level +" Where "+ COLUMN_pseudo + " = '" + pseudo+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(selectQuery);
    }

    public int getLastLevel(String pseudo){

        String selectQuery = "SELECT " +COLUMN_last_save+ " FROM " + TABLE_NAME+" WHERE "+ COLUMN_pseudo +" = '"+pseudo+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        return Integer.parseInt(c.getString(0));
    }
    public void saveMode(String pseudo, Mode mode){

        String selectQuery = "UPDATE "+ TABLE_NAME +" SET "+ COLUMN_last_mode_save+" = '"+ mode.getNomMode() +"' Where "+ COLUMN_pseudo + " = '" + pseudo+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(selectQuery);
    }

    public String getLastMode(String pseudo){

        String selectQuery = "SELECT " +COLUMN_last_mode_save+ " FROM " + TABLE_NAME+" WHERE "+ COLUMN_pseudo +" = '"+pseudo+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        return c.getString(0);
    }


    public ArrayList<User> getAllScores(){

        ArrayList<User> users = new ArrayList<User>();

        String selectQuery = "SELECT " +COLUMN_pseudo+","+COLUMN_bestscore+ " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = new User(cursor.getString(0),Float.parseFloat(cursor.getString(1)));
            users.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }

}









