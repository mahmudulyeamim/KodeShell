package com.example.kodeshell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        LinearLayout homeButton = findViewById(R.id.homeActivity);
        LinearLayout helpButton = findViewById(R.id.helpActivity);
        LinearLayout contestReminderButton = findViewById(R.id.contestReminderActivity);
        LinearLayout notificationButton = findViewById(R.id.notificationActivity);

        homeButton.setOnClickListener(view -> openHomeActivity());
        helpButton.setOnClickListener(view -> openHelpActivity());
        contestReminderButton.setOnClickListener(view -> openContestReminderActivity());
        notificationButton.setOnClickListener(view -> openNotificationActivity());
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void openHelpActivity() {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    private void openContestReminderActivity() {
        Intent intent = new Intent(this, ContestReminderActivity.class);
        startActivity(intent);
    }

    private void openNotificationActivity() {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }
}