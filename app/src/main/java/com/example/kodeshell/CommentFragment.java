package com.example.kodeshell;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentFragment extends Fragment {

    RecyclerView commentRecyclerView;
    CommentAdapter commentAdapter;

    ImageView postComment;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    EditText comment;
    String PostID, currentUserName;
    CommentDetails newComment;
    int avatarID = 0;

    private List<CommentDetails> list;

    public CommentFragment() {
        list = new ArrayList<>();
    }

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        postComment = view.findViewById(R.id.post_comment_button);
        comment = view.findViewById(R.id.comment_text);

        commentRecyclerView = view.findViewById(R.id.commentRecyclerView);

        commentAdapter = new CommentAdapter(list, getContext());

        commentRecyclerView.setAdapter(commentAdapter);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postComment.setOnClickListener(view1 -> postNewComment());

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        String uid = currentUser.getUid();

        DatabaseReference userRef = database.getReference("user").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String firstName = dataSnapshot.child("firstName").getValue(String.class);
                    String lastName = dataSnapshot.child("lastName").getValue(String.class);
                    currentUserName = firstName+" "+lastName;
                    avatarID = dataSnapshot.child("avatarid").getValue(Integer.class);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error reading data: " + databaseError.getMessage());
            }
        });
        return view;
    }

    private void postNewComment() {
        String text = comment.getText().toString().trim();

        if(text.isEmpty()) {
            Toast.makeText(getContext(), "Type a comment to post", Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference reference = database.getReference().child("post").child(getPostID()).child("comments");
            String commentId = reference.push().getKey();
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
            Map<String, Object> commentMap = new HashMap<>();
            newComment = new CommentDetails();

            loadImage(avatarID);
            newComment.setUsername(currentUserName);
            newComment.setTime(getTime(currDateTime));
            newComment.setComment(text);

            commentMap.put("username", currentUserName);
            commentMap.put("time", currDateTime);
            commentMap.put("comment", text);
            commentMap.put("avatarid", avatarID);
            reference.child(commentId).setValue(commentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                    } else {
                        Toast.makeText(getContext(), "Error in creating the user", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            list.add(0, newComment);

            commentAdapter.notifyItemInserted(0);
            commentRecyclerView.scrollToPosition(0);

            comment.setText("");
        }
    }
    private String getTime(String dateString) {
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }

        // Convert string to LocalDateTime object
        LocalDateTime givenDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            givenDateTime = LocalDateTime.parse(dateString, formatter);
        }

        // Get the current LocalDateTime
        LocalDateTime currentDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDateTime = LocalDateTime.now();
        }

        return getTimeAgo(givenDateTime, currentDateTime);
    }
    public static String getTimeAgo(LocalDateTime pastTime, LocalDateTime currentTime) {
        Duration duration = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            duration = Duration.between(pastTime, currentTime);
        }

        long seconds = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            seconds = duration.getSeconds();
        }
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        long months = days / 30;
        long years = days / 365;

        if (seconds < 60) {
            return "Just now";
        } else if (minutes == 1) {
            return "1 minute ago";
        } else if (minutes < 60) {
            return minutes + " minutes ago";
        } else if (hours == 1) {
            return "1 hour ago";
        } else if (hours < 24) {
            return hours + " hours ago";
        } else if (days == 1) {
            return "1 day ago";
        } else if (days < 7) {
            return days + " days ago";
        } else if (weeks == 1) {
            return "1 week ago";
        } else if (weeks < 4) {
            return weeks + " weeks ago";
        } else if (months == 1) {
            return "1 month ago";
        } else if (months < 12) {
            return months + " months ago";
        } else if (years == 1) {
            return "1 year ago";
        } else {
            return years + " years ago";
        }
    }
    private void loadImage(int i){
        if(i == 0) {
            newComment.setAvatar(R.drawable.avatar1);
        }
        else if(i == 1) {
            newComment.setAvatar(R.drawable.avatar2);
        }
        else if(i == 2) {
            newComment.setAvatar(R.drawable.avatar3);
        }
        else if(i == 3) {
            newComment.setAvatar(R.drawable.avatar4);
        }
        else if(i == 4) {
            newComment.setAvatar(R.drawable.avatar5);
        }
        else if(i == 5) {
            newComment.setAvatar(R.drawable.avatar6);
        }
        else if(i == 6) {
            newComment.setAvatar(R.drawable.avatar7);
        }
        else if(i == 7) {
            newComment.setAvatar(R.drawable.avatar8);
        }
        else if(i == 8) {
            newComment.setAvatar(R.drawable.avatar9);
        }
        else if(i == 9) {
            newComment.setAvatar(R.drawable.avatar10);
        }
        else if(i == 10) {
            newComment.setAvatar(R.drawable.avatar11);
        }
        else if(i == 11) {
            newComment.setAvatar(R.drawable.avatar12);
        }
    }
    public List<CommentDetails> getList() {
        return list;
    }

    public void setList(List<CommentDetails> list) {
        if(list != null) this.list = list;
    }
}