package com.example.myproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("everything")
    Call<NewsResponse> getHealthNews(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );
}


