package com.pikifeld.menu.Entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class SQLite extends SQLiteOpenHelper {


    //information of database
    private static final int DATABASE_VERSION = 1;
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

    public SQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String text="CREATE TABLE "+ TABLE_NAME + "(" + COLUMN_NOM + " Text," +  COLUMN_PRENOM+ " Text,"+COLUMN_AGE+" Text," +  COLUMN_SEXE+ " Text," +
                COLUMN_mail +  " Text," +
                COLUMN_pseudo+ " Text," +
                COLUMN_mdp +" Text,"+
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
            // insert row in students table
            insert = db.insert(TABLE_NAME, null, values);
        }else {
            insert = -1;
        }
        return insert;
    }


    public int autenticateUser(String pseudobis, String password){

        String selectQuery = "SELECT " +COLUMN_mdp+ " FROM " + TABLE_NAME+" WHERE pseudo ='"+pseudobis+"'" ;


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

    public int isBestScore(String pseudo, float score){

        String selectQuery = "SELECT " +COLUMN_bestscore+ " FROM " + TABLE_NAME+" WHERE pseudo ='"+pseudo+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
            //Float.parseFloat(c.getFloat(0.0));
        if(Integer.parseInt(c.getString(0)) < score){
            String updateQuery =  "UPDATE "+ TABLE_NAME +"SET "+ COLUMN_bestscore+" = '"+ score +"' Where "+ COLUMN_pseudo + " = '" + pseudo +"'";
            return 1;
        }else {
                 return 0;
    }}


    }









