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
    DatabaseReference reference, reference2;
    int avatarID = 0;
    String uid;

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

        postButton.setOnClickListener(view1 -> createNewPost());
        uid = currentUser.getUid();

        DatabaseReference userRef = database.getReference("user").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User currentUser = dataSnapshot.getValue(User.class);
                    if (currentUser != null) {
                        loadImage(currentUser.getAvatarid());
                        avatarID = currentUser.getAvatarid();
                        username.setText(currentUser.getFirstName()+" "+currentUser.getLastName());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error reading data: " + databaseError.getMessage());
            }
        });
        loadImage(avatarID);
        return view;
    }
    private void loadImage(int i){
        if(i == 0) {
            Picasso.get().load(R.drawable.avatar1).fit().centerInside().into(profilePic);
        }
        else if(i == 1) {
            Picasso.get().load(R.drawable.avatar2).fit().centerInside().into(profilePic);
        }
        else if(i == 2) {
            Picasso.get().load(R.drawable.avatar3).fit().centerInside().into(profilePic);
        }
        else if(i == 3) {
            Picasso.get().load(R.drawable.avatar4).fit().centerInside().into(profilePic);
        }
        else if(i == 4) {
            Picasso.get().load(R.drawable.avatar5).fit().centerInside().into(profilePic);
        }
        else if(i == 5) {
            Picasso.get().load(R.drawable.avatar6).fit().centerInside().into(profilePic);
        }
        else if(i == 6) {
            Picasso.get().load(R.drawable.avatar7).fit().centerInside().into(profilePic);
        }
        else if(i == 7) {
            Picasso.get().load(R.drawable.avatar8).fit().centerInside().into(profilePic);
        }
        else if(i == 8) {
            Picasso.get().load(R.drawable.avatar9).fit().centerInside().into(profilePic);
        }
        else if(i == 9) {
            Picasso.get().load(R.drawable.avatar10).fit().centerInside().into(profilePic);
        }
        else if(i == 10) {
            Picasso.get().load(R.drawable.avatar11).fit().centerInside().into(profilePic);
        }
        else if(i == 11) {
            Picasso.get().load(R.drawable.avatar12).fit().centerInside().into(profilePic);
        }
    }
    private void createNewPost() {
        String post = postContent.getText().toString().trim();
        if (!post.isEmpty()) {
            reference = FirebaseDatabase.getInstance().getReference().child("post");
            reference2 = FirebaseDatabase.getInstance().getReference().child("user");
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
            Post newPost = new Post(postId, uid, userName, currDateTime, content, 0, 0, avatarID);
            reference.child(postId).setValue(newPost).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        setVoters(postId);
                        setPostCount();
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
        }
    }
    private void setPostCount() {
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = database.getReference().child("user").child(currentUser);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int currentCount = dataSnapshot.child("postcount").getValue(Integer.class);
                    updatePostCount(currentUser, currentCount);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Failed to read postcount", databaseError.toException());
            }
        });
    }

    private void updatePostCount(String currentUser, int currentCount) {
        DatabaseReference reference = database.getReference().child("user").child(currentUser).child("postcount");
        reference.setValue((currentCount + 1)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Postcount updated successfully
//                    Toast.makeText(getContext(), "Postcount updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Failed to update postcount
                    Toast.makeText(getContext(), "Error in updating postcount", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setVoters(String postId){
        DatabaseReference votersRef = reference.child(postId).child("voters");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String id = userSnapshot.child("userId").getValue(String.class);
                    if (votersRef != null && votersRef.child(id) != null)
                        votersRef.child(id).setValue(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Failed to read user information", databaseError.toException());
            }
        });
    }
}