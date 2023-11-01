package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    Long timeoutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken  resendingToken;
    EditText inputOtp1, inputOtp2, inputOtp3, inputOtp4, inputOtp5, inputOtp6;

    Button submitButton;

    TextView resendButton, emailAddressText;

    String requestedPhoneNumber;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

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

        requestedPhoneNumber = getIntent().getStringExtra("requested_phone_number");
        sendOTP(requestedPhoneNumber, false);

        setEmailAddress();

        moveOtpBox();

        submitButton.setOnClickListener(view -> openResetPasswordActivity());

        resendButton.setOnClickListener(view -> resendOtp());

        backButton.setOnClickListener(view -> openForgotPasswordActivity());
    }
    private void sendOTP(String phoneNumber, boolean isResend) {
        startResendTimer();
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
//                                setInProgress(false);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
//                                setInProgress(false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
//                                Toast.makeText(OtpActivity.this, phoneNumber, Toast.LENGTH_SHORT).show();
//                                setInProgress(false);
                            }
                        });
        if (isResend) {
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }

    }

    void signIn(PhoneAuthCredential phoneAuthCredential) {
        //login and go to next activity
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(OtpActivity.this, ResetPasswordActivity.class);
//                    intent.putExtra("phone", phoneNumber);
//                    startActivity(intent);
                } else {
                    Toast.makeText(OtpActivity.this, "OTP verification failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void startResendTimer() {
        resendButton.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
//                resendOtpTextView.setText("Resend OTP in " + timeoutSeconds + " seconds");
                if (timeoutSeconds <= 0) {
                    timeoutSeconds = 60L;
                    timer.cancel();
                    runOnUiThread(() -> {
                        resendButton.setEnabled(true);
                    });
                }
            }
        }, 0, 1000);
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
        emailAddressText.setText(requestedPhoneNumber);
    }

    private void openForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void openResetPasswordActivity() {
        validateOtp();
    }

    private void validateOtp() {
        if(!inputOtp1.getText().toString().trim().isEmpty() && !inputOtp2.getText().toString().trim().isEmpty() && !inputOtp3.getText().toString().trim().isEmpty() && !inputOtp4.getText().toString().trim().isEmpty() && !inputOtp5.getText().toString().trim().isEmpty() && !inputOtp6.getText().toString().trim().isEmpty()) {
            String otp = inputOtp1.getText().toString().trim()+inputOtp2.getText().toString().trim()+inputOtp3.getText().toString().trim()+inputOtp4.getText().toString().trim()
                    +inputOtp5.getText().toString().trim()+inputOtp6.getText().toString().trim();
//            Toast.makeText(this, otp+" "+verificationCode, Toast.LENGTH_SHORT).show();
            checkOtp(otp);
        }
        else {
            Toast.makeText(this, "Please enter all the numbers", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkOtp(String otp) {
        String enteredOtp  = otp;
        Toast.makeText(this, verificationCode, Toast.LENGTH_SHORT).show();
        PhoneAuthCredential credential =  PhoneAuthProvider.getCredential(verificationCode,enteredOtp);
        signIn(credential);
    }

    private void resendOtp() {
        sendOTP(requestedPhoneNumber, true);
        // logic for sending the otp to the requested email address
    }
}