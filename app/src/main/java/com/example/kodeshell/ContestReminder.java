package com.example.kodeshell;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ContestReminder extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 123;
    private boolean isAlarmSet = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contset_reminder);

        Button setAlarmButton = findViewById(R.id.setAlarmButton);
        TimePicker timePicker = findViewById(R.id.timePicker);
        // Set the TimePicker to use the 24-hour format
        timePicker.setIs24HourView(false);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // Check if the app has the SCHEDULE_EXACT_ALARM permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                        && ContextCompat.checkSelfPermission(ContestReminder.this, Manifest.permission.SCHEDULE_EXACT_ALARM)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Request the permission from the user
                    ActivityCompat.requestPermissions(ContestReminder.this,
                            new String[]{Manifest.permission.SCHEDULE_EXACT_ALARM},
                            PERMISSION_REQUEST_CODE);
                } else {
                    // Permission is already granted, proceed to set the alarm
                    int hour = timePicker.getHour();
                    int minute = timePicker.getMinute();
                    try {
                        setAlarm(hour, minute);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to set the alarm
                TimePicker timePicker = findViewById(R.id.timePicker);
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                try {
                    setAlarm(hour, minute);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(this, "Permission denied. Cannot set the alarm.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Set the alarm
    @SuppressLint("ScheduleExactAlarm")
    private void setAlarm(int hour, int minute) throws ParseException {
        // Calculate the time in milliseconds
        String starttime=getIntent().getStringExtra("starttime");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = dateFormat.parse(starttime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 6-hour);
        calendar.add(Calendar.MINUTE, -minute);

        // Create an intent for the BroadcastReceiver
        Intent alarmIntent = new Intent(ContestReminder.this, AlarmReceiver.class);
        alarmIntent.putExtra("name",getIntent().getStringExtra("name"));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                ContestReminder.this,
                0,
                alarmIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Get the AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // Notify the user that the alarm is set
//        Toast.makeText(ContestReminder.this, "Alarm set successfully", Toast.LENGTH_SHORT).show();
    }

}
