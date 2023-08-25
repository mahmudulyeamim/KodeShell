package com.example.kodeshell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        LinearLayout homeButton = findViewById(R.id.homeActivity);
        LinearLayout helpButton = findViewById(R.id.helpActivity);
        LinearLayout contestReminderButton = findViewById(R.id.contestReminderActivity);
        LinearLayout messageButton = findViewById(R.id.messageActivity);

        homeButton.setOnClickListener(view -> openHomeActivity());
        helpButton.setOnClickListener(view -> openHelpActivity());
        contestReminderButton.setOnClickListener(view -> openContestReminderActivity());
        messageButton.setOnClickListener(view -> openMessageActivity());
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

    private void openMessageActivity() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }
}