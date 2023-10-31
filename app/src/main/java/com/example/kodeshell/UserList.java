package com.example.kodeshell;

import static java.lang.Math.max;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserList extends AppCompatActivity {
    private String id;
    private RecyclerView recyclerView;
    List<UserTime> tempContacts = new ArrayList<>();
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
        database = FirebaseDatabase.getInstance();
        database = FirebaseDatabase.getInstance();
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recyclerView = findViewById(R.id.recyclerViewContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search = findViewById(R.id.searchEditText);
        DatabaseReference reference = database.getReference().child("user");
        ImageButton filterButton = findViewById(R.id.roundImageButton);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String reciever_id = userSnapshot.child("userId").getValue(String.class);
//                    String senderRoom = id + reciever_id;
//                    long last_time[] = {Long.MIN_VALUE};
//                    AtomicInteger timestampCounter = new AtomicInteger(0);
//                    getTimeStamp(senderRoom, new TimeStampCallback() {
//                        @Override
//                        public void onTimeStampReceived(long timeStamp, int totalUsers) {
//                            // Use the timeStamp here
//                            Log.d("kat", String.valueOf(timeStamp));
//                            last_time[0] = max(last_time[0], timeStamp);
//                            timestampCounter.incrementAndGet();
//                            if (timestampCounter.get() == totalUsers) {
//                                String fname = userSnapshot.child("firstName").getValue(String.class);
//                                String lname = userSnapshot.child("lastName").getValue(String.class);
//                                String email = userSnapshot.child("mail").getValue(String.class);
//                                String pass = userSnapshot.child("password").getValue(String.class);
//                                String status = userSnapshot.child("status").getValue(String.class);
//
//                                String phone = userSnapshot.child("phonenumber").getValue(String.class);
//                                User new_user = new User(reciever_id, fname, lname, email, pass, status, 0, "", "", "", phone);
//                                tempContacts.add(new UserTime(new_user, last_time[0]));
//                                // All timestamps received, call activateAdapter
//                                activateAdapter();
//                            }
//                        }
//                    });
                    String fname = userSnapshot.child("firstName").getValue(String.class);
                    String lname = userSnapshot.child("lastName").getValue(String.class);
                    String email = userSnapshot.child("mail").getValue(String.class);
                    String pass = userSnapshot.child("password").getValue(String.class);
                    String status = userSnapshot.child("status").getValue(String.class);
                    String phone = userSnapshot.child("phonenumber").getValue(String.class);
                    int avatarID = userSnapshot.child("avatarid").getValue(Integer.class);
                    User new_user = new User(reciever_id, fname, lname, email, pass, status, avatarID, "", "", "", phone, 0, 0);
                    contacts.add(new_user);
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
//    private void getTimeStamp(String senderRoom, TimeStampCallback callback) {
//        FirebaseDatabase new_database = FirebaseDatabase.getInstance();
//        long last_time[] = {Long.MIN_VALUE};
//        new_database.getReference().child("chats")
//                .child(senderRoom)
//                .child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        int totalUsers = (int) snapshot.getChildrenCount();
//                        if (snapshot.exists()) {
//                            for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
//                                if (messageSnapshot.child("timeStamp").getValue() != null) {
//                                    Object timestampObject = messageSnapshot.child("timeStamp").getValue();
//                                    if (timestampObject != null) {
//                                        long timestamp = Long.parseLong(timestampObject.toString());
//                                        last_time[0] = max(last_time[0], timestamp);
//                                    }
//                                }
//                            }
//                            // Call the callback with the obtained timestamp
//                            callback.onTimeStampReceived(last_time[0], totalUsers);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        // Handle onCancelled if needed
//                    }
//                });
//    }
//
//    // Define a callback interface
//    interface TimeStampCallback {
//        void onTimeStampReceived(long timeStamp, int totalUsers);
//    }
    private void activateAdapter() {
//        Collections.sort(tempContacts, Comparator.comparingLong(UserTime::getTimeStamp));
//        for (int i = 0; i < tempContacts.size(); ++i) {
//            Log.d("rko", tempContacts.get(i).getUser().firstName + tempContacts.get(i).getTimeStamp());
//            contacts.add(tempContacts.get(i).getUser());
//        }
        adapter = new UserListAdapter(this, contacts);
        recyclerView.setAdapter(adapter);
    }
}
