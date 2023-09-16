package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    TextView signupButton, forgotPasswordButton;

    Button loginButton, loginWithGoogleButton;

    TextInputLayout emailTextInput, passwordTextInput;

    FirebaseAuth auth;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.login_button);
        loginWithGoogleButton = findViewById(R.id.login_google_button);

        emailTextInput = findViewById(R.id.login_email);
        passwordTextInput = findViewById(R.id.login_password);

        signupButton = findViewById(R.id.registerButtonLoginActivity);
        forgotPasswordButton = findViewById(R.id.login_forgot_password_text);

        progressBar = findViewById(R.id.login_progress_bar);

        initEditText();

        loginButton.setOnClickListener(view -> validateUser());

        signupButton.setOnClickListener(view -> openSignupActivity1());

        forgotPasswordButton.setOnClickListener(view -> openForgotPasswordActivity());

        loginWithGoogleButton.setOnClickListener(view -> validateUserByGoogle());
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
    }

    private void validateUser() {
        if(validateEmail() && validatePassword()) {
            progressBar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.INVISIBLE);

            String email = Objects.requireNonNull(emailTextInput.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(passwordTextInput.getEditText()).getText().toString().trim();

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);

                    if (task.isSuccessful()){
//                                progressDialog.show();
                        try {
                            openHomeActivity();
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, UserList.class);
        startActivity(intent);
        finish();
    }

    private void openSignupActivity1() {
        Intent intent = new Intent(this, SignupActivity1.class);
        startActivity(intent);
        finish();
    }

    private void openForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
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

    private boolean validatePassword() {
        String passwordInput = Objects.requireNonNull(passwordTextInput.getEditText()).getText().toString().trim();

        if(passwordInput.isEmpty()) {
            passwordTextInput.setError("Field can't be empty");
            return false;
        }
        else {
            passwordTextInput.setError(null);
            return true;
        }
    }

    private void validateUserByGoogle() {
        // logic to check user through google account
    }
}