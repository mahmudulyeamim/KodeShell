package com.example.kodeshell;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity1 extends AppCompatActivity {
    TextView loginbut;
    EditText rg_firstname, rg_lastname, rg_email;
    Button rg_signup;
    CircleImageView rg_profileImg;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

    FirebaseDatabase database;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        loginbut = findViewById(R.id.loginButtonSignupActivity1);
        rg_firstname = findViewById(R.id.firstnameSignupActivity1);
        rg_lastname = findViewById(R.id.lastnameSignupActivity1);
        rg_email = findViewById(R.id.emailSignupActivity1);
        rg_signup = findViewById(R.id.continueButtonSignupActivity1);

        Button continueButton = findViewById(R.id.continueButtonSignupActivity1);
        ImageView backButton = findViewById(R.id.backButton);

        continueButton.setOnClickListener(view -> openSignupActivity2());

        backButton.setOnClickListener(view -> openLoginActivity());
    }

    private void openSignupActivity2() {
        String firstName, lastName, email;
        firstName = rg_firstname.getText().toString();
        lastName = rg_lastname.getText().toString();
        email = rg_email.getText().toString();
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                TextUtils.isEmpty(email)) {
            Toast.makeText(SignupActivity1.this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            rg_email.setError("Type A Valid Email Here");
        } else {
            Intent intent = new Intent(this, SignupActivity2.class);
            intent.putExtra("fname", firstName);
            intent.putExtra("lname", lastName);
            intent.putExtra("uemail", email);
            startActivity(intent);
        }
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}