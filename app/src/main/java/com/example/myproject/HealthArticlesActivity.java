package com.example.myproject;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import android.util.Log;


public class HealthArticlesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("HealthArticlesActivity", "Activity Created");

        setContentView(R.layout.activity_health_articles); // Make sure this layout exists

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchHealthArticles();
    }

    private void fetchHealthArticles() {
        APIService apiService = RetroFitClient.getRetrofit().create(APIService.class);

        Call<NewsResponse> call = apiService.getHealthNews("health", "adfc963837f6437996207e272efd3dbd");

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Article> articles = response.body().getArticles();
                    adapter = new NewsAdapter(HealthArticlesActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d("HealthArticlesActivity", "Response failed or body is null");
                }

            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(HealthArticlesActivity.this, "Failed to fetch news", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

