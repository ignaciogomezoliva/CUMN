package com.example.cumn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Puntuacion extends AppCompatActivity {
    public static final String TAG = "TAG";
    private SharedPreferences preferences;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String scoreId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacion);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String dif;
            String cat;
            switch (preferences.getInt("Cat", 0)) {
                case 0:
                    cat = "entertainment";
                    break;
                case 1:
                    cat = "science";
                    break;
                case 21:
                    cat = "sports";
                    break;
                case 22:
                    cat = "geography";
                    break;
                case 23:
                    cat = "history";
                    break;
                case 25:
                    cat = "art";
                    break;
                default:
                    cat = "normal";
                    break;
            }
            switch (preferences.getInt("Dif", 0)) {
                case 0:
                    dif = "easy";
                    break;
                case 1:
                    dif = "medium";
                    break;
                case 2:
                    dif = "hard";
                    break;
                default:
                    dif = "easy";
                    break;
            }
            int value = extras.getInt("punt");

            userID = fAuth.getCurrentUser().getUid();
            scoreId = LocalDateTime.now().toString();
            DocumentReference documentReference = fStore
                    .collection("users").document(userID)
                    .collection("score").document(scoreId);

            Map<String,Object> score = new HashMap<>();
            score.put("category",cat);
            score.put("difficulty",dif);
            score.put("score", value);
            documentReference.set(score).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG,"onSuccess: score " + scoreId + " added for "+ userID);
                }
            });

            ((TextView)findViewById(R.id.pts)).setText(String.valueOf(value));
        }
    }

    public void inicio(View view){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}