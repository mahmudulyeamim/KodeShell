package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class UserList extends AppCompatActivity {
    private String id;
    private RecyclerView recyclerView;
    List<User> contacts = new ArrayList<>();
    private UserListAdapter adapter;
    //    private ValueEventListener valueEventListener;
    FirebaseDatabase database;
    TextView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        database = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.recyclerViewContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search = findViewById(R.id.searchEditText);
        DatabaseReference reference = database.getReference().child("user");
        ImageButton filterButton = findViewById(R.id.roundImageButton);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String fname = userSnapshot.child("firstName").getValue(String.class);
                    String lname = userSnapshot.child("lastName").getValue(String.class);
                    String email = userSnapshot.child("mail").getValue(String.class);
                    String pass = userSnapshot.child("password").getValue(String.class);
                    String status = userSnapshot.child("status").getValue(String.class);
                    String userId = userSnapshot.child("userId").getValue(String.class);
                    User new_user = new User(userId, fname, lname, email, pass, status, 0, "", "", "");
                    contacts.add(new_user);
                    String stringValue = Integer.toString(contacts.size());
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
    }

    private void activateAdapter() {
        adapter = new UserListAdapter(this, contacts);
        recyclerView.setAdapter(adapter);
    }
}
