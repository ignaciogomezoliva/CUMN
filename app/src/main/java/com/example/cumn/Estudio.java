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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
        ServiceAPi.getInstance()
                .normal(numPreguntas, dif, "multiple")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x -> {
                    List<Result> respuestas = x.getResults();

                    for(int i=0; i<numPreguntas;i++){
                        preguntas.add(Jsoup.parse(respuestas.get(i).getQuestion()).text());
                        respuestasR.add(Jsoup.parse(respuestas.get(i).getCorrectAnswer()).text());
                    }

                    initRecyclerView();
                });

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(preguntas, respuestasR);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}