package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView registerButton = findViewById(R.id.registerButtonLoginActivity);
        TextView forgotPasswordButton = findViewById(R.id.forgotPasswordLoginActivity);
        Button loginButton = findViewById(R.id.loginButtonLoginActivity);

        registerButton.setOnClickListener(view -> openSignupActivity1());
        forgotPasswordButton.setOnClickListener(view -> openForgotPasswordActivity());
        loginButton.setOnClickListener(view -> openHomeActivity());
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void openSignupActivity1() {
        Intent intent = new Intent(this, SignupActivity1.class);
        startActivity(intent);
    }

    private void openForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}