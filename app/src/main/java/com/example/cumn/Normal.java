package com.example.cumn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cumn.rest.ITriviaAPI;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Normal extends AppCompatActivity {

    private List<String> buena = new ArrayList<String>();
    private int puntos;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        buena.add(0, "");

    }

    public void siguientePregunta(View view){
        System.out.println(view.getId());
        System.out.println(((Button)view).getText());
        System.out.println(buena.get(buena.size()-1));

        if (buena.size()>1){
            if(((Button)view).getText().equals(buena.get(buena.size()-1)))
                puntos +=5;
            else
                puntos -=2;
        }
        System.out.println(puntos);
        System.out.println(buena.size());

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

            ServiceAPi.getInstance().normal(1, dif, "multiple").enqueue(new Callback<Pregunta>() {
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
            System.out.println(puntos);
            Intent intent = new Intent(this, Puntuacion.class);
            startActivity(intent);
        }

    }
}


