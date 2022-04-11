package com.example.cumn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Difficulty extends AppCompatActivity {
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

    }

    //0: fácil
    //1: medio
    //2: difícil

    public void setDifficultyEasy(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Dif", 0).apply();
        System.out.println("Dificultad: " + preferences.getInt("Dif", 0));

        changeActivity();
    }

    public void setDifficultyMed(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Dif", 1).apply();
        System.out.println("Dificultad: " + preferences.getInt("Dif", 0));

        changeActivity();
    }

    public void setDifficultyHard(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Dif", 2).apply();
        System.out.println("Dificultad: " + preferences.getInt("Dif", 0));

        changeActivity();
    }

    private void changeActivity() {
        String activity = preferences.getString("Prev", "");
        Intent intent = null;
        if (activity.equals("Normal"))
            intent = new Intent(this, Normal.class);
        else if (activity.equals("Categorias"))
            intent = new Intent(this, CategoriasG.class);
        else
            intent = new Intent(this, Estudio.class);

        startActivity(intent);
    }
}