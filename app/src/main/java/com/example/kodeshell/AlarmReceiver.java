package com.example.kodeshell;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // This method will be called when the alarm goes off.
        // You can perform your desired actions here.

        // Create a notification channel (required for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "alarm_channel";
            String channelName = "Alarm Channel";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        // Create a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "alarm_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Contest Reminder")
                .setContentText(intent.getStringExtra("name"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true); // Auto-cancel the notification when clicked

        // Create a notification manager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // Show the notification
        int notificationId = 123; // Unique ID for the notification
        notificationManager.notify(notificationId, builder.build());
    }


}
