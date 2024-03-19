package com.example.tarea04;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class JuegoBolas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, PantallaFin.class);

        //Primer contador, para pasar a la siguiente pantalla. Tarda en funcion de la duración de la partida
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable runnable = new Runnable() {

            //Objeto preferencias creado en la anterior actividad y rescatado aquí

            Preferencias p = (Preferencias) getIntent().getSerializableExtra("p");

            //Contador antes mencionado
            int countdownStarter = p.getTiempoJuego() + 5;

            //Empieza el contador
            public void run() {
                countdownStarter--;
                if (countdownStarter < 0) {
                    scheduler.shutdown();
                    openPantallaFin(p);
                }
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);


        //Se pinta le marco de juego
        setContentView(new DrawView(this));
    }

    public class DrawView extends View {

        //Pinceles tanto para la bola como para el texto de preparacion
        public Paint pincelBola;
        public Paint pincelText;

        private static final int RADIO = 30;

        //Randomizador para usar en la dirección inicial de las bolas
        public Random randomizador = new Random();

        //Otro rescate del objeto preferencias antes mencionado
        Preferencias p = (Preferencias) getIntent().getSerializableExtra("p");
        int numBolas = p.getNumBolas();

        //Array de objetos BolasParam, clase para instanciar bolas
        private BolaParam[] bolas = new BolaParam[numBolas];


        //Variables para el funcionamiento de una máquina de estados que posteriormente utilizaré
        int estado = 0;
        int segundos = 0;
        int tick = 0;

        //Variables para posicion de los textos, cambios de direccion de las bolas y segundo contador (el de juego, se muestra en pantalla)
        int textPos = 0;
        int velocidad = p.getVelocidad();
        int tiempoTotal = p.getTiempoJuego();

        //Array de colores para acceder rápido a ellos
        int[] colores = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK};


        public DrawView(Context context) {
            super(context);
            pincelBola = new Paint();
            pincelText = new Paint();
            pincelText.setColor(Color.BLACK);

            //Cambiamos la fuente del texto de preparación
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = getResources().getFont(R.font.fuente2);
                pincelText.setTypeface(typeface);
            }

            pincelText.setTextSize(100);

            //Dos array de enteros tanto para la direccion inicial (aleatoria) como para los diferentes colores que habra en el array de bolas
            int[] coefVelo = new int[]{-1, 1};
            int[] bolaColores = new int[numBolas];


            //asignamos un numero a cada color segun el array COLORES, y montamos bolaColores segun las Preferencias
            for (int j = 0; j < p.getNumRojos(); j++) {
                bolaColores[j] = 0;
            }
            for (int j = 0; j < p.getNumVerdes(); j++) {
                bolaColores[p.getNumRojos() + j] = 1;
            }
            for (int j = 0; j < p.getNumAzules(); j++) {
                bolaColores[p.getNumRojos() + p.getNumVerdes() + j] = 2;
            }
            for (int j = 0; j < p.getNumAmarillos(); j++) {
                bolaColores[p.getNumRojos() + p.getNumVerdes() + p.getNumAzules() + j] = 3;
            }

            for (int j = 0; j < p.getNumMagentas(); j++) {
                bolaColores[p.getNumRojos() + p.getNumVerdes() + p.getNumAzules() + p.getNumAmarillos() + j] = 4;
            }

            //Ahora si, inicializamos el array de bolas aleatorizando la dirección inicial
            for (int i = 0; i < numBolas; i++) {

                int posX = randomizador.nextInt(1000);
                int posY = randomizador.nextInt(1000);
                int posCoefVelo = randomizador.nextInt(2);

                bolas[i] = new BolaParam(colores[bolaColores[i]], velocidad * coefVelo[posCoefVelo], velocidad * coefVelo[posCoefVelo], posX, posY, p.isRebote());

            }


        }

        @Override
        protected void onSizeChanged(int w, int h, int oldW, int oldH) {

        }

        protected void onDraw(Canvas c) {
            // Anchura
            int w = getWidth();

            // Altura
            int h = getHeight();

            // Límites de pantalla
            int limiteDerecha = w - RADIO;
            int limiteInferior = h - RADIO;

            //Pintamos background de juego como el resto del juego
            setBackgroundColor(getResources().getColor(R.color.purple_200));


            //MAQUINA DE ESTADOS. Me servira para cambiar entre los distintos estados del juego.
            switch (estado) {
                case 0:

                    // PREPARADOS
                    textPos += 30;
                    c.drawText("PREPARADOS", (w / 2) - 300, h - textPos, pincelText);
                    if (textPos >= h) {
                        textPos = 0;
                        estado++;
                    }
                    break; //Cambio de estados, pintamos siguiente texto
                case 1:
                    // LISTOS
                    textPos += 30;
                    c.drawText("LISTOS", (w / 2) - 200, h - textPos, pincelText);
                    if (textPos >= h) {
                        textPos = getHeight();
                        estado++;
                    }
                    break; //Cambio de estados, pintamos bolas
                case 2:
                    tick++;
                    for (int i = 0; i < bolas.length; i++) {

                        // Elegir color
                        pincelBola.setColor(bolas[i].color);

                        bolas[i].posX += bolas[i].velocidadX;
                        bolas[i].posY += bolas[i].velocidadY;


                        if (bolas[i].posX >= limiteDerecha) {
                            if (bolas[i].rebote) {
                                bolas[i].posX = limiteDerecha;
                                bolas[i].velocidadX *= -1;
                            } else {
                                bolas[i].posX = 50;

                            }
                        }
                        if (bolas[i].posX <= RADIO) {
                            if (bolas[i].rebote) {
                                bolas[i].posX = RADIO;
                                bolas[i].velocidadX *= -1;
                            } else {
                                bolas[i].posX = limiteDerecha;

                            }
                        }
                        if (bolas[i].posY >= limiteInferior) {
                            if (bolas[i].rebote) {
                                bolas[i].posY = limiteInferior;
                                bolas[i].velocidadY *= -1;
                            } else {
                                bolas[i].posY = 50;

                            }
                        }
                        if (bolas[i].posY <= RADIO) {
                            if (bolas[i].rebote) {
                                bolas[i].posY = RADIO;
                                bolas[i].velocidadY *= -1;
                            } else {

                                bolas[i].posY = limiteInferior;
                            }
                        }


                        //Dos bucles if para el contador
                        if (tick == 70) {
                            tick = 0;
                            segundos++;
                        }

                        if (segundos == tiempoTotal) {
                            estado++;
                        }
                        // Dibujar el círculo
                        c.drawCircle(bolas[i].posX, bolas[i].posY, RADIO, pincelBola);

                        // Dibujar contador
                        c.drawText(Integer.toString(tiempoTotal - segundos), 100, 200, pincelText);
                    }
                    break;
                case 3: //Último estado, fin del juego

                    break;

            }
            postInvalidateDelayed(10);
        }
    }

    public void openPantallaFin(Preferencias p) {

        Intent intent = new Intent(this, PantallaFin.class);

        //Tenemos que volver a enviar nuestro objeto Preferencias
        intent.putExtra("p", p);
        startActivity(intent);
    }

}
