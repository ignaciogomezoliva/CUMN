package com.example.cumn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

    }

    public void changeLang(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Lang", 2).apply();
        System.out.println("Preferencias: ");
        System.out.println(preferences.getInt("Lang", 0));
        Intent intent = new Intent(this, Lang.class);
        startActivity(intent);
    }
}