package com.example.kodeshell;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SubmissionHolder extends RecyclerView.ViewHolder{
    TextView problemName, submissionStatus, submissionTime;
    CardView submissionCardView;

    public SubmissionHolder(@NonNull View itemView) {
        super(itemView);
        problemName = itemView.findViewById(R.id.problem_name_textview);
        submissionStatus = itemView.findViewById(R.id.solution_status_textview);
        submissionTime = itemView.findViewById(R.id.submission_time_textview);
        submissionCardView = itemView.findViewById(R.id.submissionCardView);
    }
}
