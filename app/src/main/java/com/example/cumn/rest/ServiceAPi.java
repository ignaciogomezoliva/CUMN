package com.example.cumn.rest;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceAPi {
    /// https://opentdb.com/api.php?amount=1
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();
    private static ITriviaAPI service;

    public static ITriviaAPI getInstance(){
        if (service == null)
            service = retrofit.create(ITriviaAPI.class);
        return service;
    }
}
