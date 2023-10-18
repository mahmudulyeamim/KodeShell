package com.example.kodeshell;

import android.os.Bundle;

import androidx.core.app.BundleCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class StalkFragment extends Fragment {

    TextInputLayout cfUsername, acUsername, lcUsername;
    Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stalk, container, false);

        cfUsername = view.findViewById(R.id.stalk_codeforces_username);
        acUsername = view.findViewById(R.id.stalk_atcoder_username);
        lcUsername = view.findViewById(R.id.stalk_leetcode_username);

        searchButton = view.findViewById(R.id.stalk_search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle result = new Bundle();
//                result.putString("cf", cfUsername.getEditText().getText().toString().trim());
//                result.putString("ac", acUsername.getEditText().getText().toString().trim());
//                result.putString("lc", lcUsername.getEditText().getText().toString().trim());
//                getParentFragmentManager().setFragmentResult("usernames", result);

                StalkProfileFragment stalkProfileFragment = new StalkProfileFragment();

                stalkProfileFragment.setCfUsername(cfUsername.getEditText().getText().toString().trim());
                stalkProfileFragment.setAcUsername(acUsername.getEditText().getText().toString().trim());
                stalkProfileFragment.setLcUsername(lcUsername.getEditText().getText().toString().trim());


                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment_container, stalkProfileFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}