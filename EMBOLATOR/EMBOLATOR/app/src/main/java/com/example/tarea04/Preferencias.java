package com.example.tarea04;

import java.io.Serializable;
import java.util.Random;

public class Preferencias implements Serializable {


    public Random randomizador = new Random();
    private int velocidad;
    private int tiempoJuego;
    private boolean rebote;
    private int numColores;
    private int numMaxCadaColor;
    private int numRojos;
    private int numVerdes;
    private int numAzules;
    private int numAmarillos;
    private int numMagentas;
    private int numBolas;


    //Constructor de preferencias, usando numeros aleatorios podremos construir la cantidad de colores que habrá
    public Preferencias(int velocidad, int tiempoJuego,  int numMaxCadaColor,int numColores, boolean rebote) {
        this.velocidad=velocidad;
        this.tiempoJuego=tiempoJuego;
        this.numMaxCadaColor = numMaxCadaColor;
        this.numColores = numColores;
        this.rebote=rebote;

        //Si numcolores = 3, se aleatorizan rojas, verdes y azules, prestando atencion en el numero maximo de cada color que le hemos enviado desde la pantalla principal
        if (numColores == 3) {
            numRojos = randomizador.nextInt(numMaxCadaColor) + 1;
            numVerdes = randomizador.nextInt(numMaxCadaColor) + 1;
            numAzules = randomizador.nextInt(numMaxCadaColor) + 1;
            numAmarillos = 0;
            numMagentas = 0;
            numBolas = numRojos + numVerdes + numAzules + numAmarillos + numMagentas;
        }

        //Si subimos en numero, vamos añadiendo colores
        if (numColores == 4) {
            numRojos = randomizador.nextInt(numMaxCadaColor) + 1;
            numVerdes = randomizador.nextInt(numMaxCadaColor) + 1;
            numAzules = randomizador.nextInt(numMaxCadaColor) + 1;
            numAmarillos = randomizador.nextInt(numMaxCadaColor) + 1;
            numMagentas = 0;
            numBolas = numRojos + numVerdes + numAzules + numAmarillos + numMagentas;
        }

        if (numColores == 5) {
            numRojos = randomizador.nextInt(numMaxCadaColor) + 1;
            numVerdes = randomizador.nextInt(numMaxCadaColor) + 1;
            numAzules = randomizador.nextInt(numMaxCadaColor) + 1;
            numAmarillos = randomizador.nextInt(numMaxCadaColor) + 1;
            numMagentas = randomizador.nextInt(numMaxCadaColor) + 1;
            numBolas = numRojos + numVerdes + numAzules + numAmarillos + numMagentas;
        }

    }

    //metodos para acceder a las propiedades de las preferencias
    public int getNumRojos() {
        return numRojos;
    }

    public int getNumVerdes() {
        return numVerdes;
    }

    public int getNumAzules() {
        return numAzules;
    }

    public int getNumAmarillos() {
        return numAmarillos;
    }

    public int getNumMagentas() {
        return numMagentas;
    }

    public int getNumBolas() {
        return numBolas;
    }

    public boolean isRebote() {
        return rebote;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getTiempoJuego() {
        return tiempoJuego;
    }
}
