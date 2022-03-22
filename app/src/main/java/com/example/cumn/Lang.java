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
            changeActivity();
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
}