package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class OtpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Button submitButton = findViewById(R.id.submitButtonOtpActivity);
        ImageView backButton = findViewById(R.id.backButton);

        submitButton.setOnClickListener(view -> openResetPasswordActivity());

        backButton.setOnClickListener(view -> openForgotPasswordActivity());
    }

    private void openForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void openResetPasswordActivity() {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }
}