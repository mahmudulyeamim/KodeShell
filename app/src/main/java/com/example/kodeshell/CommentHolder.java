package com.example.kodeshell;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentHolder extends RecyclerView.ViewHolder {

    ImageView profilePic;
    TextView username, time, comment;

    public CommentHolder(@NonNull View itemView) {
        super(itemView);
        profilePic = itemView.findViewById(R.id.comment_text_img);

        username = itemView.findViewById(R.id.comment_text_username);
        time = itemView.findViewById(R.id.comment_text_time);
        comment = itemView.findViewById(R.id.comment_text_content);
    }
}
