package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView postRecyclerView;
    PostAdapter postAdapter;

    ImageView messengerButton, newPostButton, searchButton;

    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseDatabase database;
    ArrayList<PostDetails> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        messengerButton = view.findViewById(R.id.home_messenger_button);
        newPostButton = view.findViewById(R.id.home_new_post_button);
        searchButton = view.findViewById(R.id.home_search_button);
        swipeRefreshLayout = view.findViewById(R.id.home_swipe_refresh_layout);

        postRecyclerView = view.findViewById(R.id.postRecyclerView);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id = postSnapshot.child("id").getValue(String.class);
                    String username = postSnapshot.child("userName").getValue(String.class);
                    String time = getTime(postSnapshot.child("time").getValue(String.class));
                    int upVote = postSnapshot.child("upVote").getValue(Integer.class);
                    int downVote = postSnapshot.child("downVote").getValue(Integer.class);
                    String post = postSnapshot.child("content").getValue(String.class);

                    // Fetch comments for each post
                    fetchCommentsForPost(id, username, time, upVote, downVote, post);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Failed to read user information", databaseError.toException());
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                PostDetails obj = new PostDetails();
//                obj.setAvatar(R.drawable.avatar12);
//                obj.setUsername("Oleksandr Kulkov");
//                obj.setTime("Just now");
//                obj.setPost("I am new to this app");
//                obj.setUpVote(10);
//                obj.setDownVote(3);
//                obj.setUpVoteIcon(R.drawable.up_voted);
//                obj.setDownVoteIcon(R.drawable.down_vote);
//                list.add(0, obj);
//
//                postAdapter.notifyItemInserted(0);
                postRecyclerView.scrollToPosition(0);
//                postAdapter = new PostAdapter(list, getContext(), getParentFragmentManager());
//                postRecyclerView.setAdapter(postAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        postAdapter = new PostAdapter(list, getContext(), getParentFragmentManager());
        postRecyclerView.setAdapter(postAdapter);

        messengerButton.setOnClickListener(view1 -> openMessengerActivity());

        newPostButton.setOnClickListener(view1 -> openNewPostFragment());

        return view;
    }
    private void fetchCommentsForPost(String postId, String username, String time, int upVote, int downVote, String post) {
        ArrayList<CommentDetails> clist = new ArrayList<>();
        DatabaseReference commentsRef = database.getReference().child("post").child(postId).child("comments");
        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clist.clear();
                for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                    String commentUsername = commentSnapshot.child("userName").getValue(String.class);
                    String commentTime = getTime(commentSnapshot.child("time").getValue(String.class));
                    String commentText = commentSnapshot.child("comment").getValue(String.class);
                    CommentDetails newComment = new CommentDetails();
                    newComment.setAvatar(R.drawable.avatar7);
                    newComment.setUsername(commentUsername);
                    newComment.setTime(commentTime);
                    newComment.setComment(commentText);
                    clist.add(newComment);
                }

                // Create and add the post after fetching comments
                PostDetails newPost = new PostDetails();
                newPost.setAvatar(R.drawable.avatar7);
                newPost.setId(postId);
                newPost.setUsername(username);
                newPost.setTime(time);
                newPost.setPost(post);
                newPost.setUpVote(upVote);
                newPost.setDownVote(downVote);
                newPost.setUpVoteIcon(R.drawable.up_vote);
                newPost.setDownVoteIcon(R.drawable.down_voted);
                newPost.setComments(clist);
                list.add(newPost);

                // Update the UI after both posts and comments are fetched
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Failed to read user information", databaseError.toException());
            }
        });
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

        // Calculate the difference
        Duration duration = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            duration = Duration.between(givenDateTime, currentDateTime);
        }
        String durationString = formatDuration(duration);
        return durationString;
    }
    private String formatDuration(Duration duration) {
        long days = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            days = duration.toDays();
        }
        long hours = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            hours = duration.toHours() % 24;
        }
        long minutes = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            minutes = duration.toMinutes() % 60;
        }
        long seconds = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            seconds = duration.getSeconds() % 60;
        }

        return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds);
    }
    private void openMessengerActivity() {
        Intent intent = new Intent(getContext(), UserList.class);
        startActivity(intent);
    }

    private void openNewPostFragment() {
        NewPostFragment newPostFragment = new NewPostFragment();

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, newPostFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void activateAdapter(ArrayList<PostDetails> list) {
        postAdapter = new PostAdapter(list, getContext(), getParentFragmentManager());
        postRecyclerView.setAdapter(postAdapter);
    }
}