package com.example.kodeshell;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;

public class SignupActivity1 extends AppCompatActivity {
    TextView loginButton;

    TextInputLayout firstNameTextInput, lastNameTextInput, emailTextInput, phoneTextInput;

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.signup1_login_text);

        firstNameTextInput = findViewById(R.id.signup1_first_name);
        lastNameTextInput = findViewById(R.id.signup1_last_name);
        emailTextInput = findViewById(R.id.signup1_email);
        phoneTextInput = findViewById(R.id.signup1_phone_number_layout);

        initEditText();

        Button continueButton = findViewById(R.id.signup1_continue_button);
        ImageView backButton = findViewById(R.id.backButton);

        continueButton.setOnClickListener(view -> openSignupActivity2());

        backButton.setOnClickListener(view -> openLoginActivity());
    }

    private void initEditText() {
        firstNameTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                firstNameTextInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lastNameTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lastNameTextInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

    private void openSignupActivity2() {
        if(validateName(firstNameTextInput) && validateName(lastNameTextInput) && validateEmail() && validatePhone()) {
            Intent intent = new Intent(this, SignupActivity2.class);
            String firstName = Objects.requireNonNull(firstNameTextInput.getEditText()).getText().toString().trim();
            String lastName = Objects.requireNonNull(lastNameTextInput.getEditText()).getText().toString().trim();
            String email = Objects.requireNonNull(emailTextInput.getEditText()).getText().toString().trim();
            String phoneNumber = Objects.requireNonNull(phoneTextInput.getEditText()).getText().toString().trim();

            intent.putExtra("fname", firstName);
            intent.putExtra("lname", lastName);
            intent.putExtra("uemail", email);
            intent.putExtra("phone", phoneNumber);

            startActivity(intent);
        }
    }

    private void openLoginActivity() {
        Intent intent = new Intent(SignupActivity1.this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean validateName(TextInputLayout nameTextInput) {
        String name = Objects.requireNonNull(nameTextInput.getEditText()).getText().toString().trim();

        if(name.isEmpty()) {
            nameTextInput.setError("Field can't be empty");
            return false;
        }
        else if(!name.matches("^[a-zA-Z]+$")) {
            nameTextInput.setError("Only alphabetical characters are allowed");
            return false;
        }
        else {
            nameTextInput.setError(null);
            return true;
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
        else {
            emailTextInput.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        String phoneInput = Objects.requireNonNull(phoneTextInput.getEditText()).getText().toString().trim();

        if(phoneInput.isEmpty()) {
            phoneTextInput.setError("Field can't be empty");
            return false;
        }
        else if(checkPhoneExistence(phoneInput)) {
            phoneTextInput.setError("Another user already exists with this phone number");
            return false;
        }
        else {
            phoneTextInput.setError(null);
            return true;
        }
    }

    private boolean checkPhoneExistence(String phoneNumber) {
        // will check in the database if there is any user with this phone number
        return false;
    }
}