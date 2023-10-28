package com.example.kodeshell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment;
    ContestFragment contestFragment;
    ProfileFragment profileFragment;

    StalkFragment stalkFragment;

    FirebaseDatabase database;

    FirebaseAuth mAuth;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        reference = database.getReference().child("user");

        homeFragment = new HomeFragment();
        contestFragment = new ContestFragment();
        profileFragment = new ProfileFragment();
        stalkFragment = new StalkFragment();

        openHomeFragment();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home_button) {
                    openHomeFragment();
                    return true;
                }
                else if(item.getItemId() == R.id.contest_button) {
                    openContestFragment();
                    return true;
                }
                else if(item.getItemId() == R.id.profile_button) {
                    loadCurrentUserInformation();
                    return true;
                }
                else if(item.getItemId() == R.id.stalk_button) {
                    openStalkFragment();
                    return true;
                }
                return false;
            }
        });
    }

    private void openStalkFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, stalkFragment).commit();
    }

    private void openHomeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, homeFragment).commit();
    }

    private void openContestFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, contestFragment).commit();
    }

    private void openProfileFragment(User user) {
        profileFragment.setUser(user);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, profileFragment).commit();
    }

    private void loadCurrentUserInformation() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference currentUserRef = reference.child(userId);
            currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User currentUser = dataSnapshot.getValue(User.class);

                        openProfileFragment(currentUser);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }
}