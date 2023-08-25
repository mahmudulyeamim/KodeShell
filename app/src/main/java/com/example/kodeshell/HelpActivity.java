package com.example.kodeshell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        LinearLayout homeButton = findViewById(R.id.homeActivity);
        LinearLayout contestReminderButton = findViewById(R.id.contestReminderActivity);
        LinearLayout notificationButton = findViewById(R.id.notificationActivity);
        LinearLayout messageButton = findViewById(R.id.messageActivity);

        homeButton.setOnClickListener(view -> openHomeActivity());
        contestReminderButton.setOnClickListener(view -> openContestReminderActivity());
        notificationButton.setOnClickListener(view -> openNotificationActivity());
        messageButton.setOnClickListener(view -> openMessageActivity());
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
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

    private void openMessageActivity() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }
}