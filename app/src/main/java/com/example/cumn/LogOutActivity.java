package com.example.cumn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LogOutActivity extends AppCompatActivity {
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);
        changeActivity();
    }

    private void changeActivity() {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
    }
}