package com.example.kodeshell;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StalkProfileViewPagerAdapter extends FragmentStateAdapter {

    private String cfUsername, acUsername, lcUsername;

    public StalkProfileViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String cfUsername, String acUsername, String lcUsername) {
        super(fragmentActivity);
        this.cfUsername = cfUsername;
        this.acUsername = acUsername;
        this.lcUsername = lcUsername;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return new ProfileAtCoderFragment(acUsername);
            case 2: return new ProfileLeetCodeFragment(lcUsername);
            default: return new ProfileCodeForcesFragment(cfUsername);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
