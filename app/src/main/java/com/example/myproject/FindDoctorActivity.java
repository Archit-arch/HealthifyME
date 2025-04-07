package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class FindDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username", "");
        Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();

        // Initialize the card views
        //CardView exit = findViewById(R.id.cardExit);
        CardView dentist = findViewById(R.id.dentist);
        CardView cardiologists = findViewById(R.id.cardiologists);
        CardView surgeons = findViewById(R.id.surgeons);
        CardView dieticians = findViewById(R.id.dietician);


        // Set OnClickListeners

        dentist.setOnClickListener(this::onCardClick);
        cardiologists.setOnClickListener(this::onCardClick);
        surgeons.setOnClickListener(this::onCardClick);
        dieticians.setOnClickListener(this::onCardClick);
    }

    // Method to handle card clicks
    public void onCardClick(View view) {
        int viewId = view.getId(); // Get the ID of the clicked view
        Intent intent;

        if (viewId == R.id.dentist) {
            intent = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            intent.putExtra("title", "Dentist");
            startActivity(intent);
        } else if (viewId == R.id.cardiologists) {
            intent = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            intent.putExtra("title", "Cardiologists");
            startActivity(intent);
        } else if (viewId == R.id.surgeons) {
            intent = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            intent.putExtra("title", "Surgeon");
            startActivity(intent);
        } else if (viewId == R.id.dietician) {
            intent = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            intent.putExtra("title", "Dietician");
            startActivity(intent);
        }
        // Add more conditions for other cards if necessary
    }

}
