package com.example.kodeshell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubmissionAdapter extends RecyclerView.Adapter<SubmissionHolder> {

    ArrayList<SubmissionDetails> list;


    public SubmissionAdapter(ArrayList<SubmissionDetails> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SubmissionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.submission_row, parent, false);
        return new SubmissionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubmissionHolder holder, int position) {
        holder.problemName.setText(list.get(position).getName());
        holder.submissionStatus.setText(list.get(position).getStatus());
        holder.submissionTime.setText(list.get(position).getTime());

        holder.submissionCardView.setOnClickListener(view -> openProblemPage(list.get(position).getUrl()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void openProblemPage(String url) {
    }
}

