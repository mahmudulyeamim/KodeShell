package com.example.kodeshell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button submitButton = findViewById(R.id.submitButtonForgotPasswordActivity);

        ImageView backButton = findViewById(R.id.backButton);

        submitButton.setOnClickListener(view -> openOtpActivity());

        backButton.setOnClickListener(view -> openLoginActivity());
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openOtpActivity() {
        Intent intent = new Intent(this, OtpActivity.class);
        startActivity(intent);
    }
}