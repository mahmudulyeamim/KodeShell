package com.example.kodeshell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class ContestReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_reminder);

        LinearLayout homeButton = findViewById(R.id.homeActivity);
        LinearLayout helpButton = findViewById(R.id.helpActivity);
        LinearLayout notificationButton = findViewById(R.id.notificationActivity);
        LinearLayout messageButton = findViewById(R.id.messageActivity);

        homeButton.setOnClickListener(view -> openHomeActivity());
        helpButton.setOnClickListener(view -> openHelpActivity());
        notificationButton.setOnClickListener(view -> openNotificationActivity());
        messageButton.setOnClickListener(view -> openMessageActivity());
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openHelpActivity() {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    private void openNotificationActivity() {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    private void openMessageActivity() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }
}