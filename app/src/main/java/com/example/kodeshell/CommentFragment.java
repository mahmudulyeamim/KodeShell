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
            CommentDetails newComment = new CommentDetails();
            newComment.setAvatar(R.drawable.avatar6);
            newComment.setUsername(currentUserName);
            newComment.setTime(currDateTime);
            newComment.setComment(text);

            commentMap.put("username", currentUserName);
            commentMap.put("time", currDateTime);
            commentMap.put("comment", text);
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

    public List<CommentDetails> getList() {
        return list;
    }

    public void setList(List<CommentDetails> list) {
        if(list != null) this.list = list;
    }
}