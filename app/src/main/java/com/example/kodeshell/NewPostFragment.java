package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NewPostFragment extends Fragment {

    ImageView profilePic;
    TextView username;

    EditText postContent;

    Button postButton;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_post, container, false);

        profilePic = view.findViewById(R.id.new_post_user_img);
        username = view.findViewById(R.id.new_post_username);
        postContent = view.findViewById(R.id.new_post_content);
        postButton = view.findViewById(R.id.upload_new_post_button);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();

        Picasso.get().load(R.drawable.avatar10).fit().centerInside().into(profilePic);

        postButton.setOnClickListener(view1 -> createNewPost());
        String uid = currentUser.getUid();

        DatabaseReference userRef = database.getReference("user").child(uid);
        Toast.makeText(getContext(), uid, Toast.LENGTH_SHORT).show();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String firstName = dataSnapshot.child("firstName").getValue(String.class);
                    String lastName = dataSnapshot.child("lastName").getValue(String.class);
                    username.setText(firstName + " " + lastName);
                    Toast.makeText(getContext(), "First Name: " + firstName + ", Last Name: " + lastName, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error reading data: " + databaseError.getMessage());
            }
        });
        return view;
    }
    private void createNewPost() {
        String post = postContent.getText().toString().trim();
        if (!post.isEmpty()) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("post");
            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("user");
            String userName = username.getText().toString();
            LocalDateTime currentDateTime = null;
            String currDateTime = "00:00:00";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentDateTime = LocalDateTime.now();
            }
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String currentDateTimeString = currentDateTime.format(formatter);
                currDateTime = currentDateTimeString;
            }
            String content = postContent.getText().toString();
            String postId = reference.push().getKey();
            Post newPost = new Post(postId, userName, currDateTime, content, 0, 0);
            reference.child(postId).setValue(newPost).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.remove(NewPostFragment.this);
                        transaction.commit();
                    } else {
                        Toast.makeText(getContext(), "Error in creating the user", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            DatabaseReference upvotersRef = reference.child(postId).child("upvoters");
            DatabaseReference downvotersRef = reference.child(postId).child("downvoters");
            reference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String id = userSnapshot.child("userId").getValue(String.class);
                        if (upvotersRef != null && upvotersRef.child(id) != null)
                            upvotersRef.child(id).setValue(false);
                        if (downvotersRef != null && downvotersRef.child(id) != null)
                            downvotersRef.child(id).setValue(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Firebase Error", "Failed to read user information", databaseError.toException());
                }
            });

        }

    }
}