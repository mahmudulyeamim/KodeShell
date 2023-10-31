package com.example.kodeshell;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

    ArrayList <NewsDetails> list;
    Context context;
    FragmentManager fragmentManager;

    public NewsAdapter(ArrayList<NewsDetails> list, Context context, FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_card, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.time.setText(list.get(position).getTime());
        holder.post.setText(list.get(position).getPost());

        if (list.get(position).getComments() == null) {
            holder.commentCount.setText("0");
        }
        else {
            holder.commentCount.setText(Integer.toString(list.get(position).getComments().size()));
        }
//        Log.d("safg", list.get(position).getNewsid());
        holder.commentCount.setOnClickListener(view -> openCommentSection(list.get(position).getNewsid(), list.get(position).getComments()));
        holder.commentIcon.setOnClickListener(view ->
                openCommentSection(list.get(position).getNewsid(), list.get(position).getComments()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void openCommentSection(String id, List<CommentDetails> list) {
        CommentFragment commentFragment = new CommentFragment();

        commentFragment.setList(list);
        Log.d("safi", id);
        commentFragment.setPostID(id);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment_container, commentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
