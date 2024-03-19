package com.example.tarea04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PantallaFin extends AppCompatActivity {
    private Button btnComprobar;

    private TextView rojo, verde, azul, amarillo, magenta;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_fin);
        btnComprobar = (Button) findViewById(R.id.btnComprobar);
        Preferencias p = (Preferencias) getIntent().getSerializableExtra("p");
        rojo = findViewById(R.id.tRojo);
        verde = findViewById(R.id.tVerde);
        azul = findViewById(R.id.tAzul);
        amarillo = findViewById(R.id.tAmarillo);
        magenta = findViewById(R.id.tMagenta);
        resultado = findViewById(R.id.tResultado);


        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    comprobar(p);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void comprobar(Preferencias p) throws InterruptedException {

        //Senciallos condicionales para comprobar el numero de aciertos. Para comparar usamos el objeto Preferencias inicializado al principio del programa
        int numAciertos = 0;
        if (rojo.getText().toString().equals("")
                || azul.getText().toString().equals("")
                || amarillo.getText().toString().equals("")
                || verde.getText().toString().equals("")
                || magenta.getText().toString().equals("")) {
            Toast.makeText(this, "¡Faltan colores por contestar!", Toast.LENGTH_LONG).show();
        } else {

            if (Integer.parseInt(rojo.getText().toString()) == p.getNumRojos()) {
                numAciertos++;
            }

            if (Integer.parseInt(verde.getText().toString()) == p.getNumVerdes()) {
                numAciertos++;
            }
            ;
            if (Integer.parseInt(azul.getText().toString()) == p.getNumAzules()) {
                numAciertos++;
            }

            if (Integer.parseInt(amarillo.getText().toString()) == p.getNumAmarillos()) {
                numAciertos++;
            }

            if (Integer.parseInt(magenta.getText().toString()) == p.getNumMagentas()) {
                numAciertos++;
            }

            //En caso de fallo, imprimir fallo y suena bocina
            if (numAciertos != 5) {
                resultado.setText("Aciertos: " + numAciertos + " / " + "5");
                MediaPlayer mep = MediaPlayer.create(this, R.raw.sonido_fallaste);
                mep.start();


            } else {
                resultado.setText("Aciertos: " + numAciertos + " / " + "5");
                MediaPlayer mep = MediaPlayer.create(this, R.raw.sonido_conseguido);
                mep.start();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }


    }


}