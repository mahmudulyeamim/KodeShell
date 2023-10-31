package com.example.kodeshell;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    RecyclerView newsRecyclerView;
    NewsAdapter newsAdapter;

    ImageView searchButton;

    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<NewsDetails> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        searchButton = view.findViewById(R.id.news_search_button);
        swipeRefreshLayout = view.findViewById(R.id.news_swipe_refresh_layout);

        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsRecyclerView.scrollToPosition(0);
                newsAdapter = new NewsAdapter(list, getContext(), getParentFragmentManager());
                newsRecyclerView.setAdapter(newsAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        NewsDetails obj1 = new NewsDetails();
        obj1.setPost("News...");
        obj1.setTime("Just now");
        list.add(obj1);

        newsAdapter = new NewsAdapter(list, getContext(), getParentFragmentManager());
        newsRecyclerView.setAdapter(newsAdapter);

        return view;
    }
}