package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        Button continueButton = findViewById(R.id.continueButtonSignupActivity1);
        ImageView backButton = findViewById(R.id.backButton);

        continueButton.setOnClickListener(view -> openSignupActivity2());

        backButton.setOnClickListener(view -> openLoginActivity());
    }

    private void openSignupActivity2() {
        Intent intent = new Intent(this, SignupActivity2.class);
        startActivity(intent);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}