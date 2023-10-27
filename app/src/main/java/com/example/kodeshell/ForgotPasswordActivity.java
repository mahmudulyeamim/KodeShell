package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputLayout phoneTextInput;
    Button submitButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        phoneTextInput = findViewById(R.id.forgot_password_phone_number);

        submitButton = findViewById(R.id.forgot_password_submit_button);

        backButton = findViewById(R.id.backButton);

        initEditText();

        submitButton.setOnClickListener(view -> openOtpActivity());

        backButton.setOnClickListener(view -> openLoginActivity());
    }

    private void initEditText() {
        phoneTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phoneTextInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openOtpActivity() {
        if(validateEmail()) {
            sendOTP();
            Intent intent = new Intent(this, OtpActivity.class);
            intent.putExtra("requested_phone_number", Objects.requireNonNull(phoneTextInput.getEditText()).getText().toString().trim());
            startActivity(intent);
        }
    }

    private boolean validateEmail() {
        String phoneInput = Objects.requireNonNull(phoneTextInput.getEditText()).getText().toString().trim();

        if(phoneInput.isEmpty()) {
            phoneTextInput.setError("Field can't be empty");
            return false;
        }
        else if(!checkPhoneExistence(phoneInput)) {
            phoneTextInput.setError("No user found with this email");
            return false;
        }
        else {
            phoneTextInput.setError(null);
            return true;
        }
    }

    private boolean checkPhoneExistence(String phoneNumber) {
        // will check in the database if there is any user with this phone number
        return true;
    }

    private void sendOTP() {
        Random random = new Random();
        int min = 100000; // Minimum 6-digit number
        int max = 999999; // Maximum 6-digit number
        int code = random.nextInt(max - min + 1) + min;
        // logic for sending otp to the given email through firebase
    }
}