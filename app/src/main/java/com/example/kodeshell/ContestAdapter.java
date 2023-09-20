package com.example.kodeshell;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContestAdapter extends RecyclerView.Adapter<ContestHolder> {

    ArrayList<ContestDetails> list;
    Context context;


    public ContestAdapter(ArrayList<ContestDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ContestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contest_row, parent, false);
        return new ContestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContestHolder holder, int position) {
        System.out.println(position);
        holder.contestIcon.setImageResource(list.get(position).getContestIcon());
        holder.contestName.setText(list.get(position).getContestName());
        holder.contestTime.setText(list.get(position).getContestDate() + " at " + list.get(position).getContestTime());
        holder.contestDuration.setText("Duration: " + list.get(position).getContestDuration());

        holder.contestCardView.setOnClickListener(view -> openContestInsider(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void openContestInsider(ContestDetails contestDetails) {
        Intent intent = new Intent(context, ContestInsiderActivity.class);
        intent.putExtra("image", contestDetails.getContestIcon());
        intent.putExtra("name", contestDetails.getContestName());
        intent.putExtra("date", contestDetails.getContestDate());
        intent.putExtra("time", contestDetails.getContestTime());
        intent.putExtra("duration", contestDetails.getContestDuration());
        intent.putExtra("URL",contestDetails.getUrl());
        intent.putExtra("site",contestDetails.getSite());
        intent.putExtra("st",contestDetails.getSttime());
        intent.putExtra("end",contestDetails.getEdtime());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
