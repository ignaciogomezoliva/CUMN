package com.example.cumn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;


public class Lang extends AppCompatActivity {
    private SharedPreferences preferences;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        fAuth = FirebaseAuth.getInstance();
        System.out.println("Preferencias: ");
        System.out.println(preferences.getInt("Lang", 0));
        if(preferences.getInt("Lang", 0) == 0 || preferences.getInt("Lang", 0) == 1) {
            if (preferences.getInt("Lang", 0) == 0)
                setLanguageForApp("es");
            else
                setLanguageForApp("en"); //Esto realmente no es necesario porque el idioma por defecto es el ingles, está por claridad
            changeActivity();
        }
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
        setLanguageForApp("es");
        changeActivity();

    }

    public void setLangENG(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Lang", 1).apply();
        System.out.println("Preferencias: ");
        System.out.println(preferences.getInt("Lang", 0));
        setLanguageForApp("en");
        changeActivity();
    }

    private void changeActivity() {
        if(fAuth.getCurrentUser() == null){
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
        }
    }

    private void setLanguageForApp(String languageToLoad){
        Locale locale;
        if(languageToLoad.equals("not-set")){ //use any value for default
            locale = Locale.getDefault();
        }
        else {
            locale = new Locale(languageToLoad);
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }


}