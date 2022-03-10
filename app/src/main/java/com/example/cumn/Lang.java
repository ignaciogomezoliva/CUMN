package com.example.cumn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Lang extends AppCompatActivity {
private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang);

        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

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

    //Aquí irá la actividad de la página de inicio :D
    private void changeActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}