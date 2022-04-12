package com.example.cumn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.cumn.rest.ServiceAPi;
import com.example.cumn.rest.models.Pregunta;
import com.example.cumn.rest.models.Result;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Estudio extends AppCompatActivity {

    private ArrayList<String> preguntas = new ArrayList<>();
    private ArrayList<String> respuestasR = new ArrayList<>();

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudio);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        initPreguntas();
    }

    private void initPreguntas(){

        String dif = "";
        int numPreguntas = 50;


        switch (preferences.getInt("Dif", 0)) {
            case 0:
                dif = "easy";
                break;
            case 1:
                dif = "medium";
                break;
            case 2:
                dif = "hard";
                break;
        }
        ServiceAPi.getInstance().normal(numPreguntas, dif, "multiple").enqueue(new Callback<Pregunta>() {
            @Override
            public void onResponse(Call<Pregunta> call, Response<Pregunta> response) {
                Pregunta pregunta = response.body();
                List<Result> respuestas = pregunta.getResults();

                for(int i=0; i<numPreguntas;i++){
                    preguntas.add(Jsoup.parse(respuestas.get(i).getQuestion()).text());
                    respuestasR.add(Jsoup.parse(respuestas.get(i).getCorrectAnswer()).text());
                }

                initRecyclerView();
            }

            @Override
            public void onFailure(Call<Pregunta> call, Throwable t) {
                Log.e("error", t.toString());
            }

        });



    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(preguntas, respuestasR);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}