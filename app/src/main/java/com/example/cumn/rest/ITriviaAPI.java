package com.example.cumn.rest;

import com.example.cumn.rest.models.Pregunta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ITriviaAPI {

    @GET("/api.php")
    public Call<Pregunta> normal(
            @Query("amount") int amount,
            @Query("difficulty") String difficulty,
            @Query("type") String type
    );

    @GET("/api.php")
    public Call<Pregunta> categories(
            @Query("amount") int amount,
            @Query("category") int category,
            @Query("difficulty") String difficulty,
            @Query("type") String type
    );
}
