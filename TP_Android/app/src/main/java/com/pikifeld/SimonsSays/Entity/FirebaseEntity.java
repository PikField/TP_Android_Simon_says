package com.pikifeld.SimonsSays.Entity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseEntity {


    public static void changeBestscore(float score){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("utilisateur");

        myRef.child(user.getUid()).child("bestscore").setValue(score);
        myRef.child(user.getUid()).child("lastLevel").setValue(0);
    }

    public static void saveLevelEnCours(int level,Mode mode){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("utilisateur");

        myRef.child(user.getUid()).child("lastLevel").setValue(level);
        myRef.child(user.getUid()).child("lastMode").setValue(mode.getNomMode());
    }
}
