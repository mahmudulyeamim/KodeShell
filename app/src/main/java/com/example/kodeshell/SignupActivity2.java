package com.example.kodeshell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class SignupActivity2 extends AppCompatActivity {
    TextInputLayout passwordField, confirmPasswordField;
    Button registerButton;
    ImageView backButton;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth auth;
    String fname, lname, email, Password, cPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        passwordField = findViewById(R.id.signup2_password);
        confirmPasswordField = findViewById(R.id.signup2_confirm_password);
        registerButton = findViewById(R.id.signup2_register_button);

        Intent prevIntent = getIntent();
        fname = prevIntent.getStringExtra("fname");
        lname = prevIntent.getStringExtra("lname");
        email = prevIntent.getStringExtra("uemail");

        initEditText();

        backButton = findViewById(R.id.backButton);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        backButton.setOnClickListener(view -> openSignupActivity1());
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Password = Objects.requireNonNull(passwordField.getEditText()).getText().toString().trim();
                cPassword = Objects.requireNonNull(confirmPasswordField.getEditText()).getText().toString().trim();
                String status = "Hey I'm Using This Application";
                if (isPasswordValid()) {
                    createNewUser();
                }
            }
        });
    }

    private void createNewUser() {
        Toast.makeText(SignupActivity2.this, email, Toast.LENGTH_SHORT).show();
        auth.createUserWithEmailAndPassword(email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(SignupActivity2.this, "User created Successfully!", Toast.LENGTH_SHORT).show();
                if (task.isSuccessful()) {
                    String id = task.getResult().getUser().getUid();
                    DatabaseReference reference = database.getReference().child("user").child(id);
                    String status = "Hey I'm Using This Application";
                    User users = new User(id, fname, lname, email, Password, status, 0, "", "", "");
                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SignupActivity2.this, MainActivity.class);
                                startActivity(intent);
                                finish();
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

    private boolean isPasswordValid() {
        if (Password.length() < 8) {
            passwordField.setError("Password Must Be 8 Characters Or More");
            return false;
        } else if (!Password.equals(cPassword)) {
            confirmPasswordField.setError("The Password Doesn't Match");
            return false;
        } else {
            return true;
        }
    }

    private void openSignupActivity1() {
        Intent intent = new Intent(SignupActivity2.this, SignupActivity1.class);
        startActivity(intent);
        finish();
    }

    private void initEditText() {
        passwordField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordField.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirmPasswordField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmPasswordField.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}