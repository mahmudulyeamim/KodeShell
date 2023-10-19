package com.example.kodeshell;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {

    ArrayList<PostDetails> list;
    Context context;

    FragmentManager fragmentManager;

    public PostAdapter(ArrayList<PostDetails> list, Context context, FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.post_card, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Picasso.get().load(list.get(position).getAvatar()).fit().centerInside().into(holder.profilePic);

        Picasso.get().load(list.get(position).getUpVoteIcon()).into(holder.upVoteIcon);
        Picasso.get().load(list.get(position).getDownVoteIcon()).into(holder.downVoteIcon);

        holder.username.setText(list.get(position).getUsername());
        holder.time.setText(list.get(position).getTime());
        holder.post.setText(list.get(position).getPost());

        int upVoteCount = list.get(position).getUpVote() - list.get(position).getDownVote();
        holder.upvoteCount.setText("+" + Integer.toString(upVoteCount));

        if(list.get(position).getComments() == null) {
            holder.commentCount.setText("0");
        }
        else {
            holder.commentCount.setText(Integer.toString(list.get(position).getComments().size()));
        }

        if(list.get(position).getComments() != null) {
            holder.commentCount.setOnClickListener(view -> openCommentFragment(list.get(position).getComments()));
            holder.commentIcon.setOnClickListener(view -> openCommentFragment(list.get(position).getComments()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void openCommentFragment(List<CommentDetails> list) {
        CommentFragment commentFragment = new CommentFragment();

        commentFragment.setList(list);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment_container, commentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
