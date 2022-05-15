package com.example.cumn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.cumn.rest.ServiceAPi;
import com.example.cumn.rest.TranslateAPI;

import com.example.cumn.rest.models.Pregunta;
import com.example.cumn.rest.models.Result;


import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Normal extends AppCompatActivity {

    private List<String> buena = new ArrayList<String>();
    private int puntos;
    private SharedPreferences preferences;
    List<Result> resultado = new ArrayList<>(); //Resultado de la llamada a la API
    private int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        buena.add(0, "");

        String dif = "";

        switch (preferences.getInt("Dif", 0)){
            case 0:
                dif="easy";
                break;
            case 1:
                dif="medium";
                break;
            case 2:
                dif="hard";
                break;

        }

        if(preferences.getInt("Lang", 0) == 0)
            cargarPreguntasESP(dif);
        else
            cargarPreguntasENG(dif);
        System.out.println("Preguntas cargadas");

        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                findViewById(R.id.respuesta1).setVisibility(View.VISIBLE);
                findViewById(R.id.respuesta2).setVisibility(View.VISIBLE);
                findViewById(R.id.respuesta3).setVisibility(View.VISIBLE);
                findViewById(R.id.respuesta4).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.Pregunta)).setText(R.string.tutorial);
            }
        }.start();


    }

    public void siguientePregunta(View view) throws InterruptedException {
        Button boton = (Button)view;

        if (buena.size()>1){
            if(boton.getText().equals(buena.get(buena.size()-1))){
                puntos +=5;

                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.correct_answer)
                        .setPositiveButton(R.string.nextq, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }

            else {
                puntos -= 2;

                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.incorrect_answer)
                        .setMessage(buena.get(buena.size()-1))
                        .setPositiveButton(R.string.nextq, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        }

        if(buena.size() < 13 ){

            List<String> incorrectas = resultado.get(cont).getIncorrectAnswers();
            List<String> randomList = new ArrayList<String>();
            Random rnd = new Random();

            //Jsoup parse sustituye los simbolos raros ;)
            ((TextView)findViewById(R.id.Pregunta)).setText(Jsoup.parse(resultado.get(cont).getQuestion()).text());

            buena.add(Jsoup.parse(resultado.get(cont).getCorrectAnswer()).text());

            randomList.addAll(incorrectas);
            randomList.add(resultado.get(cont).getCorrectAnswer());

            ((Button)findViewById(R.id.respuesta1)).setText(Jsoup.parse(randomList.remove(rnd.nextInt(randomList.size()))).text());

            ((Button)findViewById(R.id.respuesta2)).setText(Jsoup.parse(randomList.remove(rnd.nextInt(randomList.size()))).text());

            ((Button)findViewById(R.id.respuesta3)).setText(Jsoup.parse(randomList.remove(rnd.nextInt(randomList.size()))).text());

            ((Button)findViewById(R.id.respuesta4)).setText(Jsoup.parse(randomList.remove(rnd.nextInt(randomList.size()))).text());

            cont ++;
        } else{
            findViewById(R.id.respuesta1).setVisibility(View.GONE);
            findViewById(R.id.respuesta2).setVisibility(View.GONE);
            findViewById(R.id.respuesta3).setVisibility(View.GONE);
            findViewById(R.id.respuesta4).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.Pregunta)).setText(R.string.cargando_resultados);
            new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    pantallaPuntacion();
                }
            }.start();


        }

    }

    public void pantallaPuntacion(){
        Intent intent = new Intent(this, Puntuacion.class);
        intent.putExtra("punt", puntos);
        startActivity(intent);
    }

    public void cargarPreguntasESP(String dif){
        ServiceAPi.getInstance()
                .normal(12, dif, "multiple")
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

                            List<String> incorr = new ArrayList<>();
                            for (String i:r.getIncorrectAnswers()){
                                TranslateAPI.getInstance()
                                        .traduccion("18a62192-f4c7-6641-85c8-99f2845baf26:fx", i, "ES")
                                        .subscribe(x ->{
                                            incorr.add(x.getTranslations().get(0).getText());
                                        });
                            }
                            r.setIncorrectAnswers(incorr);

                        }

                        return pregunta;
                    }
                })
                .subscribe(x ->{
                    resultado = x.getResults();
                });


    }

    public void cargarPreguntasENG(String dif){
        ServiceAPi.getInstance()
                .normal(12, dif, "multiple")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x ->{
                    resultado = x.getResults();
                });


    }

}


