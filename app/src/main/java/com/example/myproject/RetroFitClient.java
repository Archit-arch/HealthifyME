package com.example.myproject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {
    private static final String BASE_URL = "https://newsapi.org/v2/";

    public static Retrofit getRetrofit() {
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }
}

