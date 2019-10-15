package com.pikifeld.menu.Entity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

public class ButtonTP {

    public Button button;
    private int colorDark=0;
    private int colorLight=0;
    private int numButton;

    public ButtonTP(Button button,int numButton){
        this.numButton = numButton;
        this.button = button;
    }
    public ButtonTP(Button button){
        this.button = button;
    }

    public ButtonTP(Button button,int numButton,int colorLight, int colorDark){
        this.numButton = numButton;
        this.button = button;
        this.colorDark = colorDark;
        this.colorLight = colorLight;
    }


    public int getColorDark() {
        return colorDark;
    }

    public int getColorLight() {
        return colorLight;
    }

    public void setColorDark(int colorDark) {
        this.colorDark = colorDark;
    }

    public void setColorLight(int colorLight) {
        this.colorLight = colorLight;
    }

    public void buttonLight(Context context) {
            this.button.setBackgroundColor(context.getResources().getColor(this.colorLight));
    }

    public void buttonDark(Context context) {
            this.button.setBackgroundColor(context.getResources().getColor(this.colorDark));
    }

}
