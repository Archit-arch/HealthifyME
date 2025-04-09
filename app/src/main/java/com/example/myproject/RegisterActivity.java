package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText edUsername, edPassword, edEmail, edConfirm;
    Button btnRegister;
    TextView tvExistingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the views
        edUsername = findViewById(R.id.regusername);
        edPassword = findViewById(R.id.regpassword);
        edEmail = findViewById(R.id.email);
        edConfirm = findViewById(R.id.confirmPassword);
        btnRegister = findViewById(R.id.registerbutton);
        tvExistingUser = findViewById(R.id.existButton);

        // Redirect to Login Page if "Existing User?" is clicked
        tvExistingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        // Register button click listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUsername.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String confirm = edConfirm.getText().toString();

                // Create Database helper instance
                Database db = new Database(getApplicationContext());

                // Check if fields are empty
                if (username.length() == 0 || password.length() == 0 || email.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if password and confirm password match
                    if (password.equals(confirm)) {
                        // Validate password strength
                        if (isValid(password)) {
                            // Insert user into database
                            db.register(username, email, password);
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                            // Redirect to Home Page
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            finish(); // Close RegisterActivity to avoid going back
                        } else {
                            Toast.makeText(getApplicationContext(), "Password must be at least 8 characters, containing letter, digit, and special symbol", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Password validation method
    public static boolean isValid(String password) {
        int f1 = 0, f2 = 0, f3 = 0;

        if (password.length() < 8) {
            return false;
        }
        else {
            for (int i = 0; i < password.length(); i++) {
                if (Character.isLetter(password.charAt(i))) {
                    f1 = 1;
                }
            }
            for (int i = 0; i < password.length(); i++) {
                if (Character.isDigit(password.charAt(i))) {
                    f2 = 1;
                }
            }
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if ((c >= 33 && c <= 46) || c == 64) {
                    f3 = 1;
                }
            }

            return f1 == 1 && f2 == 1 && f3 == 1;
        }
    }
}