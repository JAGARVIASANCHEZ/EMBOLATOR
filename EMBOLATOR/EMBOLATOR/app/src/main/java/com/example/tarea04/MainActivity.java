package com.example.tarea04;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Button btnComenzar;
    private Button btnPref;

    private TextView txtVel, txtTiempo, txtMaxBolas, txtNumColores, txtRebote;
    private int p1 = 0, p2 = 0, p3 = 0, p4 = 0;
    private boolean p5 = true;
    private Preferencias p;
    public SharedPreferences preferences;  //Preferencias de la partida, genrea un documento con las preferencias escogidas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnComenzar = (Button) findViewById(R.id.BComenzar);
        btnPref = (Button) findViewById(R.id.BtnPref);
        txtVel = (TextView) findViewById(R.id.txtVel);
        txtTiempo = (TextView) findViewById(R.id.txtTiempo);
        txtMaxBolas = (TextView) findViewById(R.id.txtMaxBolas);
        txtNumColores = (TextView) findViewById(R.id.txtNumColores);
        txtRebote = (TextView) findViewById(R.id.txtRebote);


        //En la primera llamada al documento de preferencias éste no existe, por lo que los valores por defecto se indican aquí (20,20,4,4,true)
        preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String vel="";
        switch (preferences.getInt("velocidad", 20)){
            case 10:
                vel="Lento";
                break;
            case 20:
                vel="Normal";
                break;
            case 30:
                vel="Extrema";
        }

        //La pantalla inicial indica las preferencias de la partida
        txtVel.setText("Velocidad: " + vel);
        txtTiempo.setText("Tiempo: " + String.valueOf(preferences.getInt("tiempo", 20))+" segundos");
        txtMaxBolas.setText("Max Bolas por cada color: " + String.valueOf(preferences.getInt("maxBolas", 4)));
        txtNumColores.setText("Número máximo de colores: " + String.valueOf(preferences.getInt("numColores", 4)));
        boolean b = preferences.getBoolean("rebote", true);
        if (b == false) {
            txtRebote.setText("Rebote: no");
        } else {
            txtRebote.setText("Rebote: si");
        }


        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openJuego();
            }
        });

        btnPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPreferencias();
            }
        });


    }

    // Se recuperan las preferencias para instanciar un objeto Preferencias, con todas las opciones de la partida
    public void openJuego() {
        Intent intent = new Intent(this, JuegoBolas.class);

        preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        p1 = preferences.getInt("velocidad", 20);
        p2 = preferences.getInt("tiempo", 20);
        p3 = preferences.getInt("maxBolas", 4);
        p4 = preferences.getInt("numColores", 4);
        p5 = preferences.getBoolean("rebote", true);


        p = new Preferencias(p1, p2, p3, p4, p5);

        intent.putExtra("p", p);
        startActivity(intent);
    }

    public void openPreferencias() {
        Intent intent = new Intent(this, MainPreferencias.class);
        startActivity(intent);
    }

}