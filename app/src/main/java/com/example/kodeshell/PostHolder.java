package com.example.kodeshell;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostHolder extends RecyclerView.ViewHolder {

    ImageView profilePic, upVoteIcon, downVoteIcon, commentIcon;
    TextView username, time, post, upvoteCount, commentCount;

    public PostHolder(@NonNull View itemView) {
        super(itemView);
        profilePic = itemView.findViewById(R.id.post_text_img);
        upVoteIcon = itemView.findViewById(R.id.post_upvote_icon);
        downVoteIcon = itemView.findViewById(R.id.post_down_vote_icon);
        commentIcon = itemView.findViewById(R.id.comment_icon);
        username = itemView.findViewById(R.id.post_text_username);
        time = itemView.findViewById(R.id.post_text_time);
        post = itemView.findViewById(R.id.post_text_content);
        upvoteCount = itemView.findViewById(R.id.post_upvote_count);
        commentCount = itemView.findViewById(R.id.post_comment_count);
    }
}
