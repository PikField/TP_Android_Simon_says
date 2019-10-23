package com.pikifeld.SimonsSays.Entity;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pikifeld.SimonsSays.ScoreBoard;
import com.pikifeld.SimonsSays.adapter.ScoreAdapter;

public class FirebaseEntity {


    public static void changeBestscore(final float score){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();


        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = database.getReference("utilisateur");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(Float.parseFloat(dataSnapshot.child(user.getUid()).child("bestscore").getValue().toString()) < score){
                        myRef.child(user.getUid()).child("bestscore").setValue(score);
                        myRef.child(user.getUid()).child("lastLevel").setValue(0);
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TEST", "loadPost:onCancelled", databaseError.toException());
            }
        });

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
