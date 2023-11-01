package com.example.kodeshell;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileViewPagerAdapter extends FragmentStateAdapter {
    FirebaseDatabase database;
    FirebaseAuth mAuth;

    DatabaseReference reference;
    User user = new User();

    String currentUserId;

    public ProfileViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String currentUserId) {

        super(fragmentActivity);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("user");

        this.currentUserId = currentUserId;

        loadCurrentUserInformation();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1: return new ProfileCodeForcesFragment(user.codeforcesuname);
            case 2: return new ProfileAtCoderFragment(user.atcoderuname);
            case 3: return new ProfileLeetCodeFragment(user.leetcodeuname);
            default: return new ProfileKodeShellFragment(currentUserId);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    private void loadCurrentUserInformation() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference currentUserRef = reference.child(currentUserId);
            currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User currentUser = dataSnapshot.getValue(User.class);
                        if (currentUser != null) {
                            user.setAvatarid(currentUser.getAvatarid());
                            user.setFirstName(currentUser.getFirstName());
                            user.setLastName(currentUser.getLastName());
                            user.setAtcoderuname(currentUser.getAtcoderuname());
                            user.setCodeforcesuname(currentUser.getCodeforcesuname());
                            user.setLeetcodeuname(currentUser.getLeetcodeuname());
                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }
}
