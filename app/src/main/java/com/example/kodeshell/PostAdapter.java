package com.example.kodeshell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {

    ArrayList<PostDetails> list;
    Context context;

    public PostAdapter(ArrayList<PostDetails> list, Context context) {
        this.list = list;
        this.context = context;
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
        // Picasso.get().load(list.get(position).getImageURL()).into(holder.profilePic);

        holder.profilePic.setImageResource(list.get(position).getAvatar());

        holder.upVoteIcon.setImageResource(list.get(position).getUpVoteIcon());
        holder.downVoteIcon.setImageResource(list.get(position).getDownVoteIcon());
        holder.username.setText(list.get(position).getUsername());
        holder.time.setText(list.get(position).getTime());
        holder.post.setText(list.get(position).getPost());
//
        int upVoteCount = list.get(position).getUpVote() - list.get(position).getDownVote();
        holder.upvoteCount.setText("+" + Integer.toString(upVoteCount));

        if(list.get(position).getComments() == null) {
            holder.commentCount.setText("0");
        }
        else {
            holder.commentCount.setText(Integer.toString(list.get(position).getComments().size()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
