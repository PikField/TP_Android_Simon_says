package com.pikifeld.menu.Entity;

import android.graphics.Color;

public class Button {

    private Button button;
    private Color colorDark;
    private Color colorLight;
    private int numButton;

    public Button(Button button,int numButton){
        this.numButton = numButton;
        this.button = button;
    }

    public Button(Button button,int numButton,Color colorLight, Color colorDark){
        this.numButton = numButton;
        this.button = button;
        this.colorDark = colorDark;
        this.colorLight = colorLight;
    }

}
