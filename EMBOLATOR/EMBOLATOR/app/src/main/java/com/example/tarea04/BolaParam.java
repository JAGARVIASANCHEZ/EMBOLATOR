package com.example.tarea04;

public class BolaParam {
    public int color;
    public int   velocidadX;
    public int   velocidadY;
    public int   posX = 0;
    public int   posY = 0;
    public boolean rebote;

    //Sencillo constructor de bolas
    public BolaParam(int color, int velocidadX, int velocidadY, int posX, int posY,boolean rebote) {
        this.color = color;
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.posX = posX;
        this.posY = posY;
        this.rebote = rebote;
    }
}
