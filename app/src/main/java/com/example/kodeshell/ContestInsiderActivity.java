package com.example.kodeshell;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContestInsiderActivity extends AppCompatActivity {

    ImageView contestIcon;
    TextView contestName, contestDate, contestTime, contestDuration;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_insider);

        contestIcon = findViewById(R.id.contest_insider_site_icon);
        contestName = findViewById(R.id.contest_insider_contest_name);
        contestDate = findViewById(R.id.contest_insider_date);
        contestTime = findViewById(R.id.contest_insider_time);
        contestDuration = findViewById(R.id.contest_insider_duration);

        contestIcon.setImageResource(getIntent().getIntExtra("image", 0));
        contestName.setText(getIntent().getStringExtra("name"));
        contestDate.setText(getIntent().getStringExtra("date"));
        contestTime.setText(getIntent().getStringExtra("time"));
        contestDuration.setText(getIntent().getStringExtra("duration"));
        URL=getIntent().getStringExtra("URL");
    }
}