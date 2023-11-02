package com.example.kodeshell;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewsFragment extends Fragment {

    RecyclerView newsRecyclerView;
    NewsAdapter newsAdapter;


    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<NewsDetails> list = new ArrayList<>();

    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        swipeRefreshLayout = view.findViewById(R.id.news_swipe_refresh_layout);

        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        database = FirebaseDatabase.getInstance();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsRecyclerView.scrollToPosition(0);
                newsAdapter = new NewsAdapter(list, getContext(), getParentFragmentManager());
                newsRecyclerView.setAdapter(newsAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        addPosts();
//        NewsDetails obj1 = new NewsDetails();
//        obj1.setPost("News...");
//        obj1.setTime("Just now");
//        list.add(obj1);

        newsAdapter = new NewsAdapter(list, getContext(), getParentFragmentManager());
        newsRecyclerView.setAdapter(newsAdapter);

        return view;
    }
    private void addPosts(){
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("adminpost");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String time = getTime(postSnapshot.child("time").getValue(String.class));
                    String post = postSnapshot.child("content").getValue(String.class);
                    String id = postSnapshot.child("id").getValue(String.class);
                    fetchCommentsForPost(id, time, post);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Failed to read user information", databaseError.toException());
            }
        });
    }
    private void fetchCommentsForPost(String id, String time, String post) {
        ArrayList<CommentDetails> clist = new ArrayList<>();
        DatabaseReference commentsRef = database.getReference().child("adminpost").child(id).child("comments");
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
                NewsDetails newPost = new NewsDetails();
                newPost.setNewsid(id);
                newPost.setTime(time);
                newPost.setPost(post);
                newPost.setComments(clist);
                if (!list.contains(newPost)) {
                    list.add(0, newPost);
                    newsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Failed to read user information", databaseError.toException());
            }
        });
    }
    private void loadImage(int i, CommentDetails newComment){
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