package com.example.kodeshell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class SignupActivity2 extends AppCompatActivity {
    TextInputLayout passwordTextInput, confirmPasswordTextInput;

    Button signupButton;

    ProgressBar progressBar;

    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth auth;
    String imageuri;
    Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        passwordTextInput = findViewById(R.id.signup2_password);
        confirmPasswordTextInput = findViewById(R.id.signup2_confirm_password);

        signupButton = findViewById(R.id.signup2_register_button);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        initEditText();

        signupButton.setOnClickListener(view -> createNewUser());

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> openSignupActivity1());
    }

    private void openSignupActivity1 () {
        Intent intent = new Intent(this, SignupActivity1.class);
        startActivity(intent);
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createNewUser() {
        if(validatePassword()) {
            progressBar.setVisibility(View.VISIBLE);
            signupButton.setVisibility(View.INVISIBLE);

            Intent prevIntent = getIntent();
            String fname = prevIntent.getStringExtra("fname");
            String lname = prevIntent.getStringExtra("lname");
            String email = prevIntent.getStringExtra("uemail");

            String password = Objects.requireNonNull(passwordTextInput.getEditText()).getText().toString().trim();
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    signupButton.setVisibility(View.VISIBLE);

                    Toast.makeText(SignupActivity2.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    if (task.isSuccessful()) {
                        String id = task.getResult().getUser().getUid();
                        DatabaseReference reference = database.getReference().child("user").child(id);
                        StorageReference storageReference = storage.getReference().child("Upload").child(id);

                        String status = "Hey I'm Using This Application";
//                                imageuri = "https://firebasestorage.googleapis.com/v0/b/av-messenger-dc8f3.appspot.com/o/man.png?alt=media&token=880f431d-9344-45e7-afe4-c2cafe8a5257";
                        User users = new User(id, fname, lname, email, password, status);
                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    openMainActivity();
                                } else {
                                    Toast.makeText(SignupActivity2.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(SignupActivity2.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
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
}