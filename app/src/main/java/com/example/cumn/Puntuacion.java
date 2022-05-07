package com.example.cumn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Puntuacion extends AppCompatActivity {

    private SharedPreferences preferences;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacion);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        fStore = FirebaseFirestore.getInstance();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String dif;
            String cat;
            switch (preferences.getInt("Cat", 0)) {
                case 0:
                    cat = "entertainment";
                    break;
                case 1:
                    cat = "science";
                    break;
                case 21:
                    cat = "sports";
                    break;
                case 22:
                    cat = "geography";
                    break;
                case 23:
                    cat = "history";
                    break;
                case 25:
                    cat = "art";
                    break;
                default:
                    cat = "normal";
                    break;
            }
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
                default:
                    dif = "easy";
                    break;
            }
            int value = extras.getInt("punt");

            //Obtener DocumentRefererence

            Map<String,Object> score = new HashMap<>();
            score.put("category",cat);
            score.put("difficulty",dif);
            score.put("score", value);

            //Insertar la score
            ((TextView)findViewById(R.id.pts)).setText(String.valueOf(value));
        }
    }

    public void inicio(View view){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}