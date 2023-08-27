package com.example.kodeshell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputLayout emailTextInput;
    Button submitButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailTextInput = findViewById(R.id.forgot_password_email);

        submitButton = findViewById(R.id.forgot_password_submit_button);

        backButton = findViewById(R.id.backButton);

        initEditText();

        submitButton.setOnClickListener(view -> openOtpActivity());

        backButton.setOnClickListener(view -> openLoginActivity());
    }

    private void initEditText() {
        emailTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailTextInput.setError(null);
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
            intent.putExtra("requested_email_address", Objects.requireNonNull(emailTextInput.getEditText()).getText().toString().trim());
            startActivity(intent);
        }
    }

    private boolean validateEmail() {
        String emailInput = Objects.requireNonNull(emailTextInput.getEditText()).getText().toString().trim();

        if(emailInput.isEmpty()) {
            emailTextInput.setError("Field can't be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailTextInput.setError("Please enter a valid email address");
            return false;
        }
        else if(!checkEmailExistence(emailInput)) {
            emailTextInput.setError("No user found with this email");
            return false;
        }
        else {
            emailTextInput.setError(null);
            return true;
        }
    }

    private boolean checkEmailExistence(String emailAddress) {
        // will check in the database if there is any user with this email address
        return true;
    }

    private void sendOTP() {
        // logic for sending otp to the given email through firebase
    }
}