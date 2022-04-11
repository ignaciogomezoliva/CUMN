package com.example.cumn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

public class Estudio extends AppCompatActivity {

    private ArrayList<String> preguntas = new ArrayList<>();
    private ArrayList<String> respuestas = new ArrayList<>();

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudio);

        initPreguntas();
    }

    private void initPreguntas(){
        //De prueba
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien1");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");
        preguntas.add("¿Cómo estas?");
        respuestas.add("Bien");

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(preguntas,respuestas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}