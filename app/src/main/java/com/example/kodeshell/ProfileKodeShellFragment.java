package com.example.kodeshell;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ProfileKodeShellFragment extends Fragment {

    RecyclerView postRecyclerView;
    PostAdapter postAdapter;

    ArrayList<PostDetails> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_kode_shell, container, false);

        postRecyclerView = view.findViewById(R.id.ks_postRecyclerView);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = getPostList();

        postAdapter = new PostAdapter(list, getContext(), getParentFragmentManager());
        postRecyclerView.setAdapter(postAdapter);

        return view;
    }

    private ArrayList<PostDetails> getPostList() {
        ArrayList<PostDetails> plist = new ArrayList<>();

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

        return plist;
    }
}