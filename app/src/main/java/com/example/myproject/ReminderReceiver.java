package com.example.myproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String medicine = intent.getStringExtra("medicine");
        Log.d("ReminderReceiver","Take Medicine"+medicine);
        Intent openIntent = new Intent(context, HomeActivity.class); // or MainActivity
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                (int) System.currentTimeMillis(),
                openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification = new Notification.Builder(context)
                .setContentTitle("Medicine Reminder")
                .setContentText("Time to take: " + medicine)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your drawable icon
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify((int) System.currentTimeMillis(), notification);
    }
}
