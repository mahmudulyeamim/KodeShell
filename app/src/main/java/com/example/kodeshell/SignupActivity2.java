package com.example.kodeshell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class SignupActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        ImageView backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(view -> openSignupActivity1());
    }

    private void openSignupActivity1() {
        Intent intent = new Intent(this, SignupActivity1.class);
        startActivity(intent);
    }
}