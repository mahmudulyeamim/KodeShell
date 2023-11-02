package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputLayout email;
    Button submitButton;
    ImageView backButton;

    Long timeoutSeconds = 60L;
    String phoneNumber;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken  resendingToken;

    ProgressBar progressBar;
    TextView resendOtpTextView;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.forgot_password_phone_number);

        submitButton = findViewById(R.id.forgot_password_submit_button);

        backButton = findViewById(R.id.backButton);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        initEditText();

        submitButton.setOnClickListener(view -> openOtpActivity());

        backButton.setOnClickListener(view -> openLoginActivity());
    }

    private void initEditText() {
        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                email.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void openOtpActivity() {
//        Toast.makeText(this, email.getEditText().toString(), Toast.LENGTH_SHORT).show();
        if(validateEmail()) {
            mAuth.sendPasswordResetEmail(email.getEditText().getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                            progressBar.setVisibility(vis);
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "Reset Password link has been sent to your registered Email", Toast.LENGTH_SHORT).show();
                            } else {
                                Exception e = task.getException();
                                if (e != null) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateEmail() {
        String phoneInput = Objects.requireNonNull(email.getEditText()).getText().toString().trim();

        if(phoneInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        }
        else if(!checkPhoneExistence(phoneInput)) {
            email.setError("No user found with this email");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }

    private boolean checkPhoneExistence(String phoneNumber) {
        // will check in the database if there is any user with this phone number
        return true;
    }

}