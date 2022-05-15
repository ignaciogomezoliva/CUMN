package com.example.cumn.rest;

import com.example.cumn.rest.models.Traduccion;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ITranslateAPI {

    @POST("/v2/translate")
    public Observable<Traduccion> traduccion(
            @Query("auth_key") String key,
            @Query("text") String text,
            @Query("target_lang") String lang
    );
}
