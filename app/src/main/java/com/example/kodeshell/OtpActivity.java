package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OtpActivity extends AppCompatActivity {

    EditText inputOtp1, inputOtp2, inputOtp3, inputOtp4, inputOtp5, inputOtp6;

    Button submitButton;

    TextView resendButton, emailAddressText;

    String requestedEmailAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        emailAddressText = findViewById(R.id.otp_email_address_text);

        inputOtp1 = findViewById(R.id.input_otp1);
        inputOtp2 = findViewById(R.id.input_otp2);
        inputOtp3 = findViewById(R.id.input_otp3);
        inputOtp4 = findViewById(R.id.input_otp4);
        inputOtp5 = findViewById(R.id.input_otp5);
        inputOtp6 = findViewById(R.id.input_otp6);

        submitButton = findViewById(R.id.otp_submit_button);

        resendButton = findViewById(R.id.otp_resend_button);

        ImageView backButton = findViewById(R.id.backButton);

        requestedEmailAddress = getIntent().getStringExtra("requested_email_address");

        setEmailAddress();

        moveOtpBox();

        submitButton.setOnClickListener(view -> openResetPasswordActivity());

        resendButton.setOnClickListener(view -> resendOtp());

        backButton.setOnClickListener(view -> openForgotPasswordActivity());
    }

    private void moveOtpBox() {

        inputOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()) {
                    inputOtp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()) {
                    inputOtp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()) {
                    inputOtp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()) {
                    inputOtp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()) {
                    inputOtp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void setEmailAddress() {
        emailAddressText.setText(requestedEmailAddress);
    }

    private void openForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void openResetPasswordActivity() {
        if(validateOtp()) {
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra("requested_email_address", requestedEmailAddress);
            startActivity(intent);
        }
    }

    private boolean validateOtp() {
        if(!inputOtp1.getText().toString().trim().isEmpty() && !inputOtp2.getText().toString().trim().isEmpty() && !inputOtp3.getText().toString().trim().isEmpty() && !inputOtp4.getText().toString().trim().isEmpty() && !inputOtp5.getText().toString().trim().isEmpty() && !inputOtp6.getText().toString().trim().isEmpty()) {
            if(checkOtp()) {
                return true;
            }
            else {
                Toast.makeText(this, "OTP didn't match", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else {
            Toast.makeText(this, "Please enter all the numbers", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkOtp() {
        // will match the otp with the one that was sent to the requested email address
        return true;
    }

    private void resendOtp() {
        // logic for sending the otp to the requested email address
    }
}