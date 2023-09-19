package com.example.kodeshell;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ContestHolder extends RecyclerView.ViewHolder{
    ImageView contestIcon;
    TextView contestName, contestTime, contestDuration;
    CardView contestCardView;
    public ContestHolder(@NonNull View itemView) {
        super(itemView);
        contestIcon = itemView.findViewById(R.id.contest_site_image);
        contestName = itemView.findViewById(R.id.contest_name_textview);
        contestTime = itemView.findViewById(R.id.contest_time_textview);
        contestDuration = itemView.findViewById(R.id.contest_duration_textview);
        contestCardView = itemView.findViewById(R.id.contestCardView);
    }
}
