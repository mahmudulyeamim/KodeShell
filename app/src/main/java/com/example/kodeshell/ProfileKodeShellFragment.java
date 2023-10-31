package com.example.kodeshell;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProfileKodeShellFragment extends Fragment {

    RecyclerView postRecyclerView;
    PostAdapter postAdapter;

    ArrayList<PostDetails> list = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_kode_shell, container, false);

        postRecyclerView = view.findViewById(R.id.ks_postRecyclerView);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getPostList();
        postAdapter = new PostAdapter(list, getContext(), getParentFragmentManager());
        postRecyclerView.setAdapter(postAdapter);

        database = FirebaseDatabase.getInstance();
        return view;
    }

    private void getPostList() {
//        ArrayList<PostDetails> plist = new ArrayList<>();

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
                    int avatarID = postSnapshot.child("avatarid").getValue(Integer.class);
                    String userID = postSnapshot.child("userID").getValue(String.class);
                    String curr_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if (userID.equals(curr_id))
                        fetchCommentsForPost(id, username, time, upVote, downVote, post, avatarID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Failed to read user information", databaseError.toException());
            }
        });
//        PostDetails obj1 = new PostDetails();
//        obj1.setPost("Hola cpers!");
//        obj1.setAvatar(R.drawable.avatar1);
//        obj1.setTime("Just now");
//        obj1.setUsername("haha haha");
//        obj1.setUpVoteIcon(R.drawable.up_voted);
//        obj1.setDownVoteIcon(R.drawable.down_vote);
//        obj1.setUpVote(3);
//        obj1.setDownVote(1);
//        obj1.setId("0");
//        plist.add(obj1);
    }

    private void fetchCommentsForPost(String postId, String username, String time, int upVote, int downVote, String post, int avatarid) {
        ArrayList<CommentDetails> clist = new ArrayList<>();
        DatabaseReference commentsRef = database.getReference().child("post").child(postId).child("comments");
        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clist.clear();
                for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                    String commentUsername = commentSnapshot.child("username").getValue(String.class);
                    String commentTime = getTime(commentSnapshot.child("time").getValue(String.class));
                    String commentText = commentSnapshot.child("comment").getValue(String.class);
                    int avatarID = commentSnapshot.child("avatarid").getValue(Integer.class);
                    CommentDetails newComment = new CommentDetails();
                    loadImage(avatarID, newComment);
                    newComment.setUsername(commentUsername);
                    newComment.setTime(commentTime);
                    newComment.setComment(commentText);
                    clist.add(newComment);
                }

                // Create and add the post after fetching comments
                PostDetails newPost = new PostDetails();
                setAvatar(newPost, avatarid);
                newPost.setId(postId);
                newPost.setUsername(username);
                newPost.setTime(time);
                newPost.setPost(post);
                newPost.setUpVote(upVote);
                newPost.setDownVote(downVote);
                newPost.setUpVoteIcon(R.drawable.up_vote);
                newPost.setDownVoteIcon(R.drawable.down_voted);
                newPost.setComments(clist);
                if (!list.contains(newPost)) {
                    list.add(0, newPost);
                    postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Failed to read user information", databaseError.toException());
            }
        });
    }

    private void loadImage(int i, CommentDetails newComment) {
        if (i == 0) {
            newComment.setAvatar(R.drawable.avatar1);
        } else if (i == 1) {
            newComment.setAvatar(R.drawable.avatar2);
        } else if (i == 2) {
            newComment.setAvatar(R.drawable.avatar3);
        } else if (i == 3) {
            newComment.setAvatar(R.drawable.avatar4);
        } else if (i == 4) {
            newComment.setAvatar(R.drawable.avatar5);
        } else if (i == 5) {
            newComment.setAvatar(R.drawable.avatar6);
        } else if (i == 6) {
            newComment.setAvatar(R.drawable.avatar7);
        } else if (i == 7) {
            newComment.setAvatar(R.drawable.avatar8);
        } else if (i == 8) {
            newComment.setAvatar(R.drawable.avatar9);
        } else if (i == 9) {
            newComment.setAvatar(R.drawable.avatar10);
        } else if (i == 10) {
            newComment.setAvatar(R.drawable.avatar11);
        } else if (i == 11) {
            newComment.setAvatar(R.drawable.avatar12);
        }
    }

    private void setAvatar(PostDetails post, int i) {
        if (i == 0) {
            post.setAvatar(R.drawable.avatar1);
        } else if (i == 1) {
            post.setAvatar(R.drawable.avatar2);
        } else if (i == 2) {
            post.setAvatar(R.drawable.avatar3);
        } else if (i == 3) {
            post.setAvatar(R.drawable.avatar4);
        } else if (i == 4) {
            post.setAvatar(R.drawable.avatar5);
        } else if (i == 5) {
            post.setAvatar(R.drawable.avatar6);
        } else if (i == 6) {
            post.setAvatar(R.drawable.avatar7);
        } else if (i == 7) {
            post.setAvatar(R.drawable.avatar8);
        } else if (i == 8) {
            post.setAvatar(R.drawable.avatar9);
        } else if (i == 9) {
            post.setAvatar(R.drawable.avatar10);
        } else if (i == 10) {
            post.setAvatar(R.drawable.avatar11);
        } else if (i == 11) {
            post.setAvatar(R.drawable.avatar12);
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
}