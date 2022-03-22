package com.example.cumn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


public class Lang extends AppCompatActivity {
private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        System.out.println("Preferencias: ");
        System.out.println(preferences.getInt("Lang", 0));
        if(preferences.getInt("Lang", 0) == 0 || preferences.getInt("Lang", 0) == 1)
            setContentView(R.layout.activity_main_menu);
        else
            setContentView(R.layout.activity_lang);


    }
    // Idioma 0: ESP
    // Idioma 1: ENG

    public void setLangESP(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Lang", 0).apply();
        System.out.println("Preferencias: ");
        System.out.println(preferences.getInt("Lang", 0));
        changeActivity();

    }

    public void setLangENG(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Lang", 1).apply();
        System.out.println("Preferencias: ");
        System.out.println(preferences.getInt("Lang", 0));
        changeActivity();
    }

    private void changeActivity() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void setPrevNormal(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Prev", "Normal").apply();
        System.out.println("Previo: " + preferences.getString("Prev", ""));
        Intent intent = new Intent(this, Difficulty.class);
        startActivity(intent);
    }

    public void setPrevCat(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Prev", "Categorias").apply();
        System.out.println("Previo: " + preferences.getString("Prev", ""));
        Intent intent = new Intent(this, Categorias.class);
        startActivity(intent);
    }

    public void setPrevContr(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Prev", "Contrarreloj").apply();
        System.out.println("Previo: " + preferences.getString("Prev", ""));
        Intent intent = new Intent(this, Difficulty.class);
        startActivity(intent);
    }

    public void changeLang(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Lang", 2).apply();
        System.out.println("Preferencias: ");
        System.out.println(preferences.getInt("Lang", -1));
        Intent intent = new Intent(this, Lang.class);
        startActivity(intent);
    }
}