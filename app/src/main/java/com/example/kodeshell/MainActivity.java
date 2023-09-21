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

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment;
    ContestFragment contestFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        homeFragment = new HomeFragment();
        contestFragment = new ContestFragment();
        profileFragment = new ProfileFragment();

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
                    openProfileFragment();
                    return true;
                }
                else if(item.getItemId() == R.id.notification_button) {
                    openMessengerActivity();
                    return true;
                }
                return false;
            }
        });
    }

    private void openMessengerActivity() {
        Intent intent = new Intent(this, UserList.class);
        startActivity(intent);
    }

    private void openHomeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, homeFragment).commit();
    }

    private void openContestFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, contestFragment).commit();
    }

    private void openProfileFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, profileFragment).commit();
    }
}