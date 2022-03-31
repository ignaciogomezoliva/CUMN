package com.example.cumn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Puntuacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacion);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("punt");
            ((TextView)findViewById(R.id.pts)).setText(String.valueOf(value));
        }
    }

    public void inicio(View view){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}