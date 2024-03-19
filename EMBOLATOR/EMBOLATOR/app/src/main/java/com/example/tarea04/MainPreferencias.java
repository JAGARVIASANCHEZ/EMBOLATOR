package com.example.tarea04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

public class MainPreferencias extends AppCompatActivity {
    private Button btnGuardar;
    private Spinner spVelocidad;
    private Spinner spTiempo;
    private Spinner spMaxBolas;
    private Spinner spNumColores;
    private Switch swRebote;
    private final String[] Velocidad = {"Extrema", "Normal", "Lento"};
    private final String[] Tiempo = {"10", "20", "30"};
    private final String[] MaxBolas = {"3", "4", "5"};
    private final String[] NumColores = {"3", "4", "5"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_preferencias);
        btnGuardar = (Button) findViewById(R.id.BtnGuardar);
        spVelocidad = (Spinner) findViewById(R.id.SpVelocidad);
        spTiempo = (Spinner) findViewById(R.id.SpTiempo);
        spMaxBolas = (Spinner) findViewById(R.id.SpMaxBolas);
        spNumColores = (Spinner) findViewById(R.id.SpNumColores);
        swRebote = findViewById(R.id.swRebote);
        ArrayAdapter<String> a1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Velocidad);
        spVelocidad.setAdapter(a1);
        ArrayAdapter<String> a2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Tiempo);
        spTiempo.setAdapter(a2);
        ArrayAdapter<String> a3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MaxBolas);
        spMaxBolas.setAdapter(a3);
        ArrayAdapter<String> a4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, NumColores);
        spNumColores.setAdapter(a4);
        swRebote.setChecked(true);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarSharedPreferences();
            }
        });

    }

    //Método para extraer las opciones del jugador de la pantalla
    public void guardarSharedPreferences() {

        SharedPreferences preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);


        int p1 = 0;
        if (spVelocidad.getSelectedItem().toString().equals("Extrema")) {
            p1 = 30;
        }
        if (spVelocidad.getSelectedItem().toString().equals("Normal")) {
            p1 = 20;
        }
        if (spVelocidad.getSelectedItem().toString().equals("Lento")) {
            p1 = 10;
        }
        int p2 = Integer.parseInt(spTiempo.getSelectedItem().toString());
        int p3 = Integer.parseInt(spMaxBolas.getSelectedItem().toString());
        int p4 = Integer.parseInt(spNumColores.getSelectedItem().toString());
        boolean p5 = swRebote.isChecked();

        //inicializamos un objeto Editor para editar el documento de Preferencias
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("velocidad", p1);
        editor.putInt("tiempo", p2);
        editor.putInt("maxBolas", p3);
        editor.putInt("numColores", p4);
        editor.putBoolean("rebote", p5);
        editor.commit();

        //Se inicializa una nueva activity Main para dejar las preferencias fijadas en la pantalla de inicio
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}




