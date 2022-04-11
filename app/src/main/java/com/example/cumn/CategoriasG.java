package com.example.cumn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class CategoriasG extends AppCompatActivity {

    private SharedPreferences preferences;
    private List<String> buena = new ArrayList<String>();
    private int puntos;
    int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_g);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        buena.add(0, "");


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

        if(buena.size() < 13){

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

            ServiceAPi.getInstance().categories(1, category, dif, "multiple").enqueue(new Callback<Pregunta>() {
                @Override
                public void onResponse(Call<Pregunta> call, Response<Pregunta> response) {
                    Pregunta pregunta = response.body();
                    List<Result> respuestas = pregunta.getResults();
                    List<String> incorrectas = respuestas.get(0).getIncorrectAnswers();
                    List<String> randomList = new ArrayList<String>();
                    Random rnd = new Random();

                    //Jsoup parse sustituye los simbolos raros ;)
                    ((TextView)findViewById(R.id.Pregunta)).setText(Jsoup.parse(respuestas.get(0).getQuestion()).text());

                    buena.add(Jsoup.parse(respuestas.get(0).getCorrectAnswer()).text());

                    randomList.addAll(incorrectas);
                    randomList.add(respuestas.get(0).getCorrectAnswer());

                    ((Button)findViewById(R.id.respuesta1)).setText(Jsoup.parse(randomList.remove(rnd.nextInt(randomList.size()))).text());

                    ((Button)findViewById(R.id.respuesta2)).setText(Jsoup.parse(randomList.remove(rnd.nextInt(randomList.size()))).text());

                    ((Button)findViewById(R.id.respuesta3)).setText(Jsoup.parse(randomList.remove(rnd.nextInt(randomList.size()))).text());

                    ((Button)findViewById(R.id.respuesta4)).setText(Jsoup.parse(randomList.remove(rnd.nextInt(randomList.size()))).text());

                }

                @Override
                public void onFailure(Call<Pregunta> call, Throwable t) {
                    Log.e("error", t.toString());
                }
            });

        } else{
            Intent intent = new Intent(this, Puntuacion.class);
            intent.putExtra("punt", puntos);
            startActivity(intent);
        }

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