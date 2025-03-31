package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username", "");
        Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();

        // Initialize the card views
        //CardView exit = findViewById(R.id.cardExit);
        CardView findDoctor = findViewById(R.id.findDoctor);
        CardView labTest = findViewById(R.id.labTest);
        //CardView orderDetails = findViewById(R.id.cardOrderDetails);
        CardView buyMedicine = findViewById(R.id.buyMedicine);
        CardView health = findViewById(R.id.healthArticle);

        // Set OnClickListeners
        //exit.setOnClickListener(this::onCardClick);
        findDoctor.setOnClickListener(this::onCardClick);
        labTest.setOnClickListener(this::onCardClick);
        //orderDetails.setOnClickListener(this::onCardClick);
        buyMedicine.setOnClickListener(this::onCardClick);
        health.setOnClickListener(this::onCardClick);
    }

    // Method to handle card clicks
    public void onCardClick(View view) {
        int viewId = view.getId(); // Get the ID of the clicked view
        Log.d("HomeActivity", "Clicked ID: " + viewId);  // Debug log
        if (viewId == R.id.findDoctor) {
            // Navigate to FindDoctorActivity
            Log.d("HomeActivity", "Launching Find Doctors");

            startActivity(new Intent(HomeActivity.this, FindDoctorActivity.class));
        } else if (viewId == R.id.labTest) {
            // Navigate to LabTestActivity
            Log.d("HomeActivity", "Launching Lab Tests");
            startActivity(new Intent(HomeActivity.this, LabTestActivity.class));
        }
        else if(viewId == R.id.buyMedicine){
            Log.d("HomeActivity", "Launching Buy Medicines");

            startActivity(new Intent(HomeActivity.this, BuyMedicineActivity.class));
        }
        else if(viewId == R.id.healthArticle) {
            Log.d("HomeActivity", "Launching Health Articles");

            startActivity(new Intent(HomeActivity.this, HealthArticlesActivity.class));
        }
        // Add more conditions for other cards if necessary
    }
}
