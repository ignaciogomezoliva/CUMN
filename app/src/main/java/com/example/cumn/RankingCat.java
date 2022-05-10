package com.example.cumn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RankingCat extends AppCompatActivity {
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_cat);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public void setNormalCat(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Cat", 20).apply();
        System.out.println("Categoría: " + preferences.getInt("Cat", 5));

        Intent intent = new Intent(this, Difficulty.class);

        startActivity(intent);
    }

    public void setCat(View view) {
        Intent intent = new Intent(this, Categorias.class);

        startActivity(intent);
    }
}