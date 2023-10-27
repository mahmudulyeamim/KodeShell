package com.example.kodeshell;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "GOOGLEAUTH";
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;

    TextView signupButton, forgotPasswordButton;

    Button loginButton, loginWithGoogleButton, signInbtn;
    ImageView googleIcon;

    TextInputLayout emailTextInput, passwordTextInput;

    ProgressBar loginprogressBar, signinprogressBar;

    String id, firstName, lastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.login_button);
        loginWithGoogleButton = findViewById(R.id.login_google_button);

        emailTextInput = findViewById(R.id.login_email);
        passwordTextInput = findViewById(R.id.login_password);

        signupButton = findViewById(R.id.registerButtonLoginActivity);
        forgotPasswordButton = findViewById(R.id.login_forgot_password_text);

        loginprogressBar = findViewById(R.id.login_progress_bar);
        signinprogressBar = findViewById(R.id.sign_in_progress_bar);

        googleIcon = findViewById(R.id.google_icon);

        initEditText();

        loginButton.setOnClickListener(view -> validateUser());

        signupButton.setOnClickListener(view -> openSignupActivity1());

        forgotPasswordButton.setOnClickListener(view -> openForgotPasswordActivity());

        loginWithGoogleButton.setOnClickListener(view -> validateUserByGoogle());

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        signInbtn = findViewById(R.id.login_google_button);
        signInbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signinprogressBar.setVisibility(View.VISIBLE);
                signInbtn.setVisibility(View.INVISIBLE);
                googleIcon.setVisibility(View.INVISIBLE);
                signIn();
            }
        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                id = account.getId();
                firstName = account.getGivenName();
                lastName = account.getFamilyName();
//                Toast.makeText(this, firstName+" "+lastName, Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        signinprogressBar.setVisibility(View.GONE);
                        signInbtn.setVisibility(View.VISIBLE);
                        googleIcon.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            userExists("yourUserId", new UserExistsCallback() {
                                @Override
                                public void onUserExists(boolean exists) {
                                    if (!exists) {
                                        User user = new User(id, firstName, lastName, "", "", "Hi", 0, "", "", "");
                                        addUser(user);
                                    }
                                    else{
                                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void addUser(User user){
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("user").child(id);
        reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    interface UserExistsCallback {
        void onUserExists(boolean exists);
    }

    void userExists(String id, UserExistsCallback callback) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("user");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean result = false;
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.child("userId").getValue(String.class);
                    if (userId != null && userId.equals(id)) {
                        result = true;
                        break; // no need to continue looping if the user is found
                    }
                }
                callback.onUserExists(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Failed to read user information", databaseError.toException());
                callback.onUserExists(false); // Handle the error case
            }
        });
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
            loginprogressBar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.INVISIBLE);

            String email = Objects.requireNonNull(emailTextInput.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(passwordTextInput.getEditText()).getText().toString().trim();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    loginprogressBar.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);

                    if (task.isSuccessful()){
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
        Intent intent = new Intent(this, MainActivity.class);
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