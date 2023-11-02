package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchUserProfileFragment extends Fragment {

    private String id;
    private RecyclerView recyclerView;
    List<User> contacts = new ArrayList<>();
    private SearchUserProfileAdapter adapter;
    FirebaseDatabase database;
    TextView search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_user_profile, container, false);

        database = FirebaseDatabase.getInstance();
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView = view.findViewById(R.id.search_user_recyclerViewUserList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        search = view.findViewById(R.id.search_user_searchEditText);
        DatabaseReference reference = database.getReference().child("user");
        ImageButton filterButton = view.findViewById(R.id.search_user_roundImageButton);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);

                    if(!user.getUserId().equals(id)) contacts.add(user);
                }
                activateAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Failed to read user information", databaseError.toException());
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter != null) {
                    String token = search.getText().toString();
                    adapter.getFilter().filter(token);
                }
            }
        });

        return view;
    }

    private void activateAdapter() {
        adapter = new SearchUserProfileAdapter(contacts, getParentFragmentManager());
        recyclerView.setAdapter(adapter);
    }
}