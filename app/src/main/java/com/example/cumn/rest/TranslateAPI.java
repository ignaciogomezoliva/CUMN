package com.example.cumn.rest;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TranslateAPI {
    /// https://api-free.deepl.com
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api-free.deepl.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();
    private static ITranslateAPI service;

    public static ITranslateAPI getInstance(){
        if (service == null)
            service = retrofit.create(ITranslateAPI.class);
        return service;
    }

}
