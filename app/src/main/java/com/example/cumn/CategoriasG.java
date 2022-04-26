package com.example.cumn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cumn.rest.ServiceAPi;
import com.example.cumn.rest.TranslateAPI;
import com.example.cumn.rest.models.Pregunta;
import com.example.cumn.rest.models.Result;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriasG extends AppCompatActivity {

    private SharedPreferences preferences;
    private List<String> buena = new ArrayList<String>();
    private int puntos;
    int category;
    List<Result> resultado=null;
    private int cont=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_g);
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

        switch (preferences.getInt("Cat", 0)){
            case 0: //Entertainment
                categoryRnd(0);
                break;
            case 1: //Science
                categoryRnd(1);
                break;
            case 21:
                category = 21;
                break;
            case 22:
                category = 22;
                break;
            case 23:
                category = 23;
                break;
            case 25:
                category = 25;
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

    public void siguientePregunta(View view){
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
                .categories(12, category,dif, "multiple")
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
                .categories(12, category, dif, "multiple")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x ->{
                    resultado = x.getResults();
                });


    }

    private void categoryRnd(int tipo){
        switch (tipo){
            case 0:
                Random rnd = new Random();
                switch(rnd.nextInt(9)){
                    case 0:
                        category = 10; //Books
                        break;
                    case 1:
                        category = 11; //Film
                        break;
                    case 2:
                        category = 12; //Music
                        break;
                    case 3:
                        category = 13; //Musical&Theaters
                        break;
                    case 4:
                        category = 14; //TV
                        break;
                    case 5:
                        category = 15; //Videogames
                        break;
                    case 6:
                        category = 16; //Boardgames
                        break;
                    case 7:
                        category = 29; //Comics
                        break;
                    case 8:
                        category = 31; //Jap Anime Manga
                        break;
                    case 9:
                        category = 32; //Cartoons
                        break;
                }
                break;

            case 1:
                Random r = new Random();
                switch(r.nextInt(3)) {
                    case 0:
                        category = 17; //Science Nature
                        break;
                    case 1:
                        category = 18; //Computers
                        break;
                    case 2:
                        category = 19; //Maths
                        break;
                    case 3:
                        category = 30; //Gadgets
                        break;
                }
                break;
        }

    }

}