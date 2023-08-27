package com.example.kodeshell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ResetPasswordActivity extends AppCompatActivity {

    TextInputLayout passwordTextInput, confirmPasswordTextInput;

    Button doneButton;

    String requestedEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        passwordTextInput = findViewById(R.id.reset_password);
        confirmPasswordTextInput = findViewById(R.id.reset_confirm_password);

        doneButton = findViewById(R.id.reset_password_done_button);

        initEditText();

        requestedEmailAddress = getIntent().getStringExtra("requested_email_address");

        doneButton.setOnClickListener(view -> openMainActivity());
    }

    private void initEditText() {
        passwordTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordTextInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirmPasswordTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmPasswordTextInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void openMainActivity() {
        if(validatePassword()) {
            Intent intent = new Intent(this, MainActivity.class);
            updateUserPassword();
            startActivity(intent);
        }
    }

    private boolean validatePassword() {
        String passwordInput = Objects.requireNonNull(passwordTextInput.getEditText()).getText().toString().trim();
        String confirmPasswordInput = Objects.requireNonNull(confirmPasswordTextInput.getEditText()).getText().toString().trim();

        if(passwordInput.isEmpty()) {
            passwordTextInput.setError("Field can't be empty");
            return false;
        }
        else if(passwordInput.length() < 6) {
            passwordTextInput.setError("Password must be atleast 6 characters long");
            return false;
        }
        else {
            if(!confirmPasswordInput.equals(passwordInput)) {
                confirmPasswordTextInput.setError("Passwords didn't match");
                return false;
            }
            else {
                return true;
            }
        }
    }

    private void updateUserPassword() {
        // will update the password of the user with the requested email address

        String passwordInput = Objects.requireNonNull(passwordTextInput.getEditText()).getText().toString().trim();

    }
}