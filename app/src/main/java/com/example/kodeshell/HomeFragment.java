package com.example.kodeshell;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView postRecyclerView;
    PostAdapter postAdapter;

    ImageView messengerButton, newPostButton, searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        messengerButton = view.findViewById(R.id.home_messenger_button);
        newPostButton = view.findViewById(R.id.home_new_post_button);
        searchButton = view.findViewById(R.id.home_search_button);

        postRecyclerView = view.findViewById(R.id.postRecyclerView);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<PostDetails> list = new ArrayList<>();

        PostDetails obj1 = new PostDetails();
        obj1.setAvatar(R.drawable.avatar1);
        obj1.setUsername("Mahmudul Yeamim");
        obj1.setTime("2 days ago");
        obj1.setPost("Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.");
        obj1.setUpVote(10);
        obj1.setDownVote(2);
        obj1.setUpVoteIcon(R.drawable.up_voted);
        obj1.setDownVoteIcon(R.drawable.down_vote);
        list.add(obj1);

        PostDetails obj2 = new PostDetails();
        obj2.setAvatar(R.drawable.avatar10);
        obj2.setUsername("Istahak Islam");
        obj2.setTime("22 hours ago");
        obj2.setPost("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.");
        obj2.setUpVote(100);
        obj2.setDownVote(21);
        obj2.setUpVoteIcon(R.drawable.up_vote);
        obj2.setDownVoteIcon(R.drawable.down_voted);
        list.add(obj2);

        PostDetails obj3 = new PostDetails();
        obj3.setAvatar(R.drawable.avatar11);
        obj3.setUsername("RIfat Khan");
        obj3.setTime("3 days ago");
        obj3.setPost("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        obj3.setUpVote(100);
        obj3.setDownVote(21);
        obj3.setUpVoteIcon(R.drawable.up_vote);
        obj3.setDownVoteIcon(R.drawable.down_vote);
        list.add(obj3);

        PostDetails obj4 = new PostDetails();
        obj4.setAvatar(R.drawable.avatar5);
        obj4.setUsername("3Ds");
        obj4.setTime("1 year ago");
        obj4.setPost("Hello world");
        obj4.setUpVote(1000);
        obj4.setDownVote(213);
        obj4.setUpVoteIcon(R.drawable.up_vote);
        obj4.setDownVoteIcon(R.drawable.down_voted);
        list.add(obj4);

        PostDetails obj5 = new PostDetails();
        obj5.setAvatar(R.drawable.avatar7);
        obj5.setUsername("Lisa Khan");
        obj5.setTime("2 year ago");
        obj5.setPost("But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?");
        obj5.setUpVote(1000);
        obj5.setDownVote(213);
        obj5.setUpVoteIcon(R.drawable.up_vote);
        obj5.setDownVoteIcon(R.drawable.down_voted);
        list.add(obj5);

        postAdapter = new PostAdapter(list, getContext());
        postRecyclerView.setAdapter(postAdapter);

        messengerButton.setOnClickListener(view1 -> openMessengerActivity());

        return view;
    }

    private void openMessengerActivity() {
        Intent intent = new Intent(getContext(), UserList.class);
        startActivity(intent);
    }
}