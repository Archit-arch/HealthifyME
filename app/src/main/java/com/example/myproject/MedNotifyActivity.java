package com.example.myproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MedNotifyActivity extends AppCompatActivity {

    EditText nameEdit, descEdit;
    Button saveButton;
    TimePicker timePicker;
    DatePicker datePicker;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_notify);

        nameEdit = findViewById(R.id.medicine_name);
        descEdit = findViewById(R.id.medicine_description);
        saveButton = findViewById(R.id.setAlarmButton);
        timePicker = findViewById(R.id.timePicker);
        datePicker = findViewById(R.id.datePicker);

        username = getIntent().getStringExtra("username");

        saveButton.setOnClickListener(v -> {
            String name = nameEdit.getText().toString().trim();
            String desc = descEdit.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(MedNotifyActivity.this, "Name and Time are required", Toast.LENGTH_SHORT).show();
                return;
            }

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, minute);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

            String formattedDate = dateFormat.format(calendar.getTime());
            String formattedTime = timeFormat.format(calendar.getTime());

            // Check if the app can schedule exact alarms
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                // Request permission from the user
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                Toast.makeText(MedNotifyActivity.this, "Grant permission to schedule exact alarms", Toast.LENGTH_LONG).show();
                return;
            }

            Database db = new Database(MedNotifyActivity.this);
            boolean inserted = db.insertMedicine(username, name, desc, formattedDate, formattedTime);

            if (inserted) {
                setAlarm(calendar, name, desc);
                Toast.makeText(MedNotifyActivity.this, "Medicine saved and Alarm set!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MedNotifyActivity.this, "Failed to save medicine", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAlarm(Calendar calendar, String medicineName, String description) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager == null) {
            Toast.makeText(this, "Alarm Manager is not available", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("medicine_name", medicineName);
        intent.putExtra("medicine_description", description);

        int requestCode = (int) System.currentTimeMillis(); // Unique request code for each alarm
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE);

        try {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent);
        } catch (SecurityException e) {
            Toast.makeText(this, "Failed to set alarm: Permission denied.", Toast.LENGTH_LONG).show();
        }
    }
}