package com.example.cumn;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Map<String, String> puntuaciones = new HashMap<>();
        List<String> nombres = new ArrayList<>();

        fStore = FirebaseFirestore.getInstance();

        fStore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            nombres.add(document.getString("username"));
                            System.out.println(document.getString("username"));
                        }
                    }
                });

        for(String user : nombres) {
            fStore.collection("users")
                    .document(user)
                    .collection("score")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        puntuaciones.put(user, document.getString("score"));
                        System.out.println(document.getString("score"));
                    }
                }
            });
        }
        for(String user : puntuaciones.keySet()) {
            System.out.println(user + ": " + puntuaciones.get(user));

        }
    }
}