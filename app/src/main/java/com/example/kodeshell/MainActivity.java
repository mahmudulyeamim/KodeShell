package com.example.kodeshell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    private void openMessageActivity() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }
}