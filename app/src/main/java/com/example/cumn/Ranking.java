package com.example.cumn;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ranking extends AppCompatActivity {
    FirebaseFirestore fStore;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_ranking);
        Map<String, Double> puntuaciones = new HashMap<>();
        List<Usuario> users = new ArrayList<>();
        String dif;
        String cat;

        switch (preferences.getInt("Dif", 0)){
            case 0:
                dif="easy";
                break;
            case 1:
                dif="medium";
                break;
            default:
                dif="hard";
                break;


        }

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

        fStore = FirebaseFirestore.getInstance();

        fStore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Usuario user = new Usuario(document.getString("username"), document.getId());
                            users.add(user);
                        }

                        for(Usuario user : users) {
                            fStore.collection("users")
                                    .document(user.getUID())
                                    .collection("score")
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Double sc = document.getDouble("score");
                                        if(document.getString("category").equals(cat) && document.getString("difficulty").equals(dif)) {
                                            if(user.isScoreNull())
                                                user.setScore(sc);
                                            else if(user.getScore() < sc)
                                                user.setScore(sc);
                                        }
                                    }
                                    if(user.isScoreNull()) users.remove(user);

                                }
                            });
                        }


                    }
                });



    }
}