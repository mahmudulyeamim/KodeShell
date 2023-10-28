package com.example.kodeshell;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EditProfileFragment extends Fragment {

    String[] avatar = {"Avatar 1", "Avatar 2", "Avatar 3", "Avatar 4", "Avatar 5", "Avatar 6", "Avatar 7", "Avatar 8",
            "Avatar 9", "Avatar 10", "Avatar 11", "Avatar 12"};

    ImageView userAvatar;
    Button saveButton;

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> arrayAdapter;

    TextInputLayout ks, cf, ac, lc;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference reference;

    User currUser;
    int avatarID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        userAvatar = view.findViewById(R.id.user_current_avatar);


        autoCompleteTextView = view.findViewById(R.id.dropdown_auto_complete_text);
        arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list_item, avatar);

        autoCompleteTextView.setAdapter(arrayAdapter);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
//        String uid = currentUser.getUid();
        reference = database.getReference().child("user");

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                avatarID = i;
                loadImage(i);
            }
        });

        ks = view.findViewById(R.id.edit_profile_kodeshell_username);
        cf = view.findViewById(R.id.edit_profile_codeforces_username);
        ac = view.findViewById(R.id.edit_profile_atcoder_username);
        lc = view.findViewById(R.id.edit_profile_leetcode_username);
        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });


        loadCurrentUserInformation();

        return view;
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

                        currUser = currentUser;

                        if (currentUser != null) {
                            loadImage(currentUser.getAvatarid());
                            avatarID = currentUser.getAvatarid();
                            ks.getEditText().setText(currentUser.getFirstName()+" "+currentUser.getLastName());
                            cf.getEditText().setText(currentUser.getCodeforcesuname());
                            ac.getEditText().setText(currentUser.getAtcoderuname());
                            lc.getEditText().setText(currentUser.getLeetcodeuname());
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }
    private void loadImage(int i){
        if(i == 0) {
            Picasso.get().load(R.drawable.avatar1).fit().centerInside().into(userAvatar);
        }
        else if(i == 1) {
            Picasso.get().load(R.drawable.avatar2).fit().centerInside().into(userAvatar);
        }
        else if(i == 2) {
            Picasso.get().load(R.drawable.avatar3).fit().centerInside().into(userAvatar);
        }
        else if(i == 3) {
            Picasso.get().load(R.drawable.avatar4).fit().centerInside().into(userAvatar);
        }
        else if(i == 4) {
            Picasso.get().load(R.drawable.avatar5).fit().centerInside().into(userAvatar);
        }
        else if(i == 5) {
            Picasso.get().load(R.drawable.avatar6).fit().centerInside().into(userAvatar);
        }
        else if(i == 6) {
            Picasso.get().load(R.drawable.avatar7).fit().centerInside().into(userAvatar);
        }
        else if(i == 7) {
            Picasso.get().load(R.drawable.avatar8).fit().centerInside().into(userAvatar);
        }
        else if(i == 8) {
            Picasso.get().load(R.drawable.avatar9).fit().centerInside().into(userAvatar);
        }
        else if(i == 9) {
            Picasso.get().load(R.drawable.avatar10).fit().centerInside().into(userAvatar);
        }
        else if(i == 10) {
            Picasso.get().load(R.drawable.avatar11).fit().centerInside().into(userAvatar);
        }
        else if(i == 11) {
            Picasso.get().load(R.drawable.avatar12).fit().centerInside().into(userAvatar);
        }
    }
    private void updateProfile(){
        String cfuname = cf.getEditText().getText().toString();
        String leetuname = lc.getEditText().getText().toString();
        String atuname = ac.getEditText().getText().toString();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            user.setAvatarid(avatarID);
                            user.setAtcoderuname(atuname);
                            user.setCodeforcesuname(cfuname);
                            user.setLeetcodeuname(leetuname);
                            userRef.setValue(user);

                            currUser = user;
                        }

                        onDataLoaded(currUser);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error
                }
            });
        }
    }

    public void onDataLoaded(User user) {
        requireActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        ProfileFragment profileFragment = new ProfileFragment();

        profileFragment.setUser(currUser);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, profileFragment);
        requireActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        transaction.commit();
    }
}