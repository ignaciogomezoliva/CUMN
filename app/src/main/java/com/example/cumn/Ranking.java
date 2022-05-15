package com.example.cumn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ranking extends AppCompatActivity {
    FirebaseFirestore fStore;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_ranking);
        ArrayList<Usuario> users = new ArrayList<>();
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

        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                ((TextView)findViewById(R.id.CargandoR)).setVisibility(View.GONE);
                initRecyclerViewR(sort(users));
                findViewById(R.id.homeR).setVisibility(View.VISIBLE);
            }
        }.start();


    }

    private void initRecyclerViewR(ArrayList<Usuario> users){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_ranking);
        RankingAdapter adapter = new RankingAdapter(users);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<Usuario> sort(ArrayList<Usuario> users){
        Comparator<Usuario> comparator =
                (Usuario u1, Usuario u2) -> u2.getScore().compareTo(u1.getScore());
        Collections.sort(users, comparator);
        return users;
    }

    public void toMainMenuR(View view) {
        Intent intent = null;
        intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}