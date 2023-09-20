package com.example.kodeshell;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ProfileViewPagerAdapter extends FragmentStateAdapter {
    public ProfileViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return new ProfileCodeForcesFragment();
            case 2: return new ProfileAtCoderFragment();
            case 3: return new ProfileLeetCodeFragment();
            default: return new ProfileKodeShellFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
