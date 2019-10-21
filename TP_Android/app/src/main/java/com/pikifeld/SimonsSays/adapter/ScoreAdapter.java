package com.pikifeld.SimonsSays.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pikifeld.SimonsSays.Entity.User;
import com.pikifeld.SimonsSays.R;

import java.util.ArrayList;


public class ScoreAdapter extends ArrayAdapter{
    //final String ajouter = "Ajouter";
    ArrayList<String> pseudo = new ArrayList<>();  //definition de varible global de l'activité
    ArrayList<Float> score = new ArrayList<>();
    Context mContext;




    public ScoreAdapter(@NonNull Context context, ArrayList<User> users) {
        super(context, R.layout.activity_score_board);
        int i=0;
        for (i=0;i<(users.size());i++) {
            this.pseudo.add(users.get(i).getPseudo());
            this.score.add(users.get(i).getBestscore());
        }
        this.mContext = context;
    }

    @Override
    public int getCount(){//retourne la longueur de la liste contenant les profils
        return pseudo.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){   //prépare les valeurs possible

        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.item_liste_score, parent, false);  //montre à quoi doit ressembler chaque éléments de la liste

            mViewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);    //trouve les composant utilisés
            mViewHolder.bestscore = (TextView) convertView.findViewById(R.id.score);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }

        mViewHolder.pseudo.setText(pseudo.get(position));
        mViewHolder.bestscore.setText(score.get(position).toString());

        return convertView;
    }
    static class ViewHolder{
        TextView pseudo;
        TextView bestscore;
    }
}
