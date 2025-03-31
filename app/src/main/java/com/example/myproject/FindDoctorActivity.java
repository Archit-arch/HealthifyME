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
        //CardView orderDetails = findViewById(R.id.cardOrderDetails);
        //CardView buyMedicine = findViewById(R.id.cardBuyMedicine);
        // CardView health = findViewById(R.id.findDoctor);

        // Set OnClickListeners
        //exit.setOnClickListener(this::onCardClick);
        dentist.setOnClickListener(this::onCardClick);
        cardiologists.setOnClickListener(this::onCardClick);
        //orderDetails.setOnClickListener(this::onCardClick);
        //buyMedicine.setOnClickListener(this::onCardClick);
        //health.setOnClickListener(this::onCardClick);
    }

    // Method to handle card clicks
    public void onCardClick(View view) {
        int viewId = view.getId(); // Get the ID of the clicked view

        if (viewId == R.id.dentist) {
            // Navigate to FindDoctorActivity
            startActivity(new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class));
        } else if (viewId == R.id.cardiologists) {
            // Navigate to LabTestActivity
            startActivity(new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class));
        }
        // Add more conditions for other cards if necessary
    }
}
