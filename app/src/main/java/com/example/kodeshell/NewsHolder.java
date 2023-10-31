package com.example.kodeshell;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsHolder extends RecyclerView.ViewHolder {

    ImageView commentIcon;
    TextView time, post, commentCount;

    public NewsHolder(@NonNull View itemView) {
        super(itemView);

        commentIcon = itemView.findViewById(R.id.news_comment_icon);
        time = itemView.findViewById(R.id.news_text_time);
        post = itemView.findViewById(R.id.news_text_content);
        commentCount = itemView.findViewById(R.id.news_comment_count);
    }
}
