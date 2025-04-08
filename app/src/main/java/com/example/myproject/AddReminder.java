package com.example.myproject;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class AddReminder extends AppCompatActivity {

    EditText edtMedicine;
    Button btnDate, btnTime, btnSet;
    Spinner spinnerRepeat;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        edtMedicine = findViewById(R.id.edtMedicine);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        btnSet = findViewById(R.id.btnSetReminder);
        spinnerRepeat = findViewById(R.id.spinnerRepeat);
        calendar = Calendar.getInstance();
        Database dbHelper = new Database(getApplicationContext());


        // Setup spinner with repeat options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.repeat_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRepeat.setAdapter(adapter);

        btnDate.setOnClickListener(v -> {
            DatePickerDialog dpd = new DatePickerDialog(AddReminder.this, (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dpd.show();
        });

        btnTime.setOnClickListener(v -> {
            TimePickerDialog tpd = new TimePickerDialog(AddReminder.this, (view, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            tpd.show();
        });

        btnSet.setOnClickListener(v -> {
            String medicine = edtMedicine.getText().toString().trim();
            if (medicine.isEmpty()) {
                Toast.makeText(this, "Please enter medicine name", Toast.LENGTH_SHORT).show();
                return;
            }

            long timeInMillis = calendar.getTimeInMillis();
            String selectedRepeat = spinnerRepeat.getSelectedItem().toString();

            long repeatIntervalMillis = 0;

            switch (selectedRepeat) {
                case "Every Day":
                    repeatIntervalMillis = AlarmManager.INTERVAL_DAY;
                    break;
                case "Every 8 Hours":
                    repeatIntervalMillis = 8 * 60 * 60 * 1000;
                    break;
                case "Every Week":
                    repeatIntervalMillis = 7 * AlarmManager.INTERVAL_DAY;
                    break;
            }

            long id = dbHelper.insertReminder(medicine, timeInMillis, repeatIntervalMillis);
            setAlarm(id, medicine, timeInMillis, repeatIntervalMillis);
            Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @SuppressLint("ScheduleExactAlarm")
    private void setAlarm(long id, String medicine, long timeInMillis, long repeatInterval) {
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("medicine", medicine);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                (int) id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {
            if (repeatInterval > 0) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, repeatInterval, pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
            }
        }
    }

}