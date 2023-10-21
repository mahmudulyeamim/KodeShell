package com.example.kodeshell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {

    List<CommentDetails> list;
    Context context;

    public CommentAdapter(List<CommentDetails> list, Context context) {
        if(list != null) this.list = list;
        else list = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comment_card, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        Picasso.get().load(list.get(position).getAvatar()).fit().centerInside().into(holder.profilePic);

        holder.username.setText(list.get(position).getUsername());
        holder.time.setText(list.get(position).getTime());
        holder.comment.setText(list.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
