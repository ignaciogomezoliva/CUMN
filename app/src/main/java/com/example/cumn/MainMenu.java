package com.example.cumn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity {
    SharedPreferences preferences;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        fAuth = FirebaseAuth.getInstance();
    }

    public void setPrevNormal(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Prev", "Normal").apply();
        editor.putInt("Cat", 20).apply();
        System.out.println("Previo: " + preferences.getString("Prev", ""));
        Intent intent = new Intent(this, Difficulty.class);
        startActivity(intent);
    }

    public void setPrevRanking(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Prev", "Ranking").apply();
        System.out.println("Previo: " + preferences.getString("Prev", ""));
        Intent intent = new Intent(this, RankingCat.class);
        startActivity(intent);
    }

    public void setPrevCat(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Prev", "Categorias").apply();
        System.out.println("Previo: " + preferences.getString("Prev", ""));
        Intent intent = new Intent(this, Categorias.class);
        startActivity(intent);
    }

    public void setPrevEst(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Prev", "Estudio").apply();
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

    public void logOut(View view) {
        fAuth.signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }
}