package com.example.cumn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cumn.rest.ServiceAPi;
import com.example.cumn.rest.TranslateAPI;
import com.example.cumn.rest.models.Pregunta;
import com.example.cumn.rest.models.Result;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Estudio extends AppCompatActivity {

    private ArrayList<String> preguntas = new ArrayList<>();
    private ArrayList<String> respuestasR = new ArrayList<>();

    List<Result> respuestas = new ArrayList<>();

    private SharedPreferences preferences;

    String dif = "";

    int numPreguntas = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudio);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
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

        if(preferences.getInt("Lang", 0) == 0)
            cargarPreguntasESP();
        else
            initPreguntas();
    }

    private void initPreguntas(){
        ServiceAPi.getInstance()
                .normal(numPreguntas, dif, "multiple")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x -> {
                    respuestas = x.getResults();
                });

        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                ((TextView)findViewById(R.id.Cargando)).setVisibility(View.GONE);
                findViewById(R.id.home).setVisibility(View.VISIBLE);
                initRecyclerView();
            }
        }.start();

    }

    public void cargarPreguntasESP(){
        System.out.println("Tengo que traducir");
        ServiceAPi.getInstance()
                .normal(numPreguntas, dif, "multiple")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Pregunta, Pregunta>() {
                    @Override
                    public Pregunta apply (Pregunta pregunta) throws Exception {
                        for(Result r:pregunta.getResults()){
                            TranslateAPI.getInstance()
                                    .traduccion("18a62192-f4c7-6641-85c8-99f2845baf26:fx", r.getQuestion(), "ES")
                                    .subscribe(x ->{
                                        r.setQuestion(x.getTranslations().get(0).getText());
                                    });

                            TranslateAPI.getInstance()
                                    .traduccion("18a62192-f4c7-6641-85c8-99f2845baf26:fx", r.getCorrectAnswer(), "ES")
                                    .subscribe(x ->{
                                        r.setCorrectAnswer(x.getTranslations().get(0).getText());
                                    });

                        }

                        return pregunta;
                    }
                })
                .subscribe(x ->{

                    respuestas = x.getResults();
                    System.out.println(respuestas.get(0).getQuestion());

                });



        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                ((TextView)findViewById(R.id.Cargando)).setVisibility(View.GONE);
                initRecyclerView();
            }
        }.start();


    }

    private void initRecyclerView(){
        for(int i=0; i<numPreguntas;i++){
            preguntas.add(Jsoup.parse(respuestas.get(i).getQuestion()).text());
            respuestasR.add(Jsoup.parse(respuestas.get(i).getCorrectAnswer()).text());
        }
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        EstudioAdapter adapter = new EstudioAdapter(preguntas, respuestasR);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void toMainMenu(View view) {
        Intent intent = null;
        intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}