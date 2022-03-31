package com.example.cumn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Categorias extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    //0: entretenimiento
    //1: ciencia
    //21: deportes
    //22: geografía
    //23: historia
    //25: arte

    public void setCategoryEntertainment(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Cat", 0).apply();
        System.out.println("Categoría: " + preferences.getInt("Cat", 0));

        changeActivity();
    }

    public void setCategoryScience(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Cat", 1).apply();
        System.out.println("Categoría: " + preferences.getInt("Cat", 1));

        changeActivity();
    }

    public void setCategorySports(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Cat", 21).apply();
        System.out.println("Categoría: " + preferences.getInt("Cat", 2));

        changeActivity();
    }

    public void setCategoryGeography(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Cat", 22).apply();
        System.out.println("Categoría: " + preferences.getInt("Cat", 3));

        changeActivity();
    }

    public void setCategoryHistory(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Cat", 23).apply();
        System.out.println("Categoría: " + preferences.getInt("Cat", 4));

        changeActivity();
    }

    public void setCategoryArt(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Cat", 25).apply();
        System.out.println("Categoría: " + preferences.getInt("Cat", 5));

        changeActivity();
    }

    private void changeActivity() {
        String activity = preferences.getString("Prev", "");
        Intent intent = null;

        intent = new Intent(this, Difficulty.class);


        startActivity(intent);
    }
}