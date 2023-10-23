package com.example.kodeshell;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

public class EditProfileFragment extends Fragment {

    String[] avatar = {"Avatar 1", "Avatar 2", "Avatar 3", "Avatar 4", "Avatar 5", "Avatar 6", "Avatar 7", "Avatar 8",
            "Avatar 9", "Avatar 10", "Avatar 11", "Avatar 12"};

    ImageView userAvatar;

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> arrayAdapter;

    TextInputLayout ks, cf, ac, lc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        userAvatar = view.findViewById(R.id.user_current_avatar);


        autoCompleteTextView = view.findViewById(R.id.dropdown_auto_complete_text);
        arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list_item, avatar);

        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
        });

        ks = view.findViewById(R.id.edit_profile_kodeshell_username);
        cf = view.findViewById(R.id.edit_profile_codeforces_username);
        ac = view.findViewById(R.id.edit_profile_atcoder_username);
        lc = view.findViewById(R.id.edit_profile_leetcode_username);

        defaultSetup();

        return view;
    }

    private void defaultSetup() {
        Picasso.get().load(R.drawable.default_profile_pic1).fit().centerInside().into(userAvatar);
        ks.getEditText().setText("Rifat Khan");
        cf.getEditText().setText("_0Istahak");
        ac.getEditText().setText("Istahak_0");
        lc.getEditText().setText("neal_wu");
    }
}