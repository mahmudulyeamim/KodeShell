package com.example.kodeshell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignupActivity2 extends AppCompatActivity {
    EditText rg_password, rg_repassword;
    Button rg_signup;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth auth;
    String imageuri;
    Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        rg_password = findViewById(R.id.passwordSignupActivity2);
        rg_repassword = findViewById(R.id.confirmPasswordSignupActivity2);
        rg_signup = findViewById(R.id.registerButtonSignupActivity2);
        Intent prevIntent = getIntent();
        String fname = prevIntent.getStringExtra("fname");
        String lname = prevIntent.getStringExtra("lname");
        String email = prevIntent.getStringExtra("uemail");
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        ImageView backButton = findViewById(R.id.backButton);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
//        backButton.setOnClickListener(view -> openSignupActivity1());
        rg_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password = rg_password.getText().toString();
                String cPassword = rg_repassword.getText().toString();
                String status = "Hey I'm Using This Application";
                if (Password.length() < 8) {
                    rg_password.setError("Password Must Be 8 Characters Or More");
                } else if (!Password.equals(cPassword)) {
                    rg_password.setError("The Password Doesn't Match");
                } else {
                    auth.createUserWithEmailAndPassword(email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(SignupActivity2.this, "sd", Toast.LENGTH_SHORT).show();
                            if (task.isSuccessful()) {
                                String id = task.getResult().getUser().getUid();
                                DatabaseReference reference = database.getReference().child("user").child(id);
                                StorageReference storageReference = storage.getReference().child("Upload").child(id);

                                String status = "Hey I'm Using This Application";
//                                imageuri = "https://firebasestorage.googleapis.com/v0/b/av-messenger-dc8f3.appspot.com/o/man.png?alt=media&token=880f431d-9344-45e7-afe4-c2cafe8a5257";
                                User users = new User(id, fname,lname,  email, Password, status);
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

            }
        });

//        private void openSignupActivity1 () {
//            Intent intent = new Intent(this, SignupActivity1.class);
//            startActivity(intent);
//        }
    }
}