package com.example.kodeshell;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Codeforces_ContestAdapter extends RecyclerView.Adapter<Codeforces_ContestAdapter.ViewHolder> {

    private List<Codeforces_Contest> codeforcesContests;
    private Context context;
    private long startTimeMillis = 0;
    private CountDownTimer countDownTimer;
    public Codeforces_ContestAdapter(Context context, List<Codeforces_Contest> codeforcesContests){
        this.context=context;
        this.codeforcesContests = codeforcesContests;
    }
    public Codeforces_ContestAdapter(List<Codeforces_Contest> codeforcesContests) {
        this.codeforcesContests = codeforcesContests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Codeforces_Contest codeforcesContest = codeforcesContests.get(position);
        holder.nameTextView.setText(codeforcesContest.getName());

        long currentTime = System.currentTimeMillis() / 1000;
        long remainingTime = codeforcesContest.getStartTimeSeconds() - currentTime;
        int durationSeconds= codeforcesContest.getDurationSeconds();
        int [] covertedduration=new int[3];
        convertSeconds(durationSeconds,covertedduration);
        holder.timeTextView.setText("Length "+covertedduration[0] + " hours " +covertedduration[1]+ " minutes ");
        startTimeMillis = remainingTime * 1000;
        // Create a countdown timer
        countDownTimer = new CountDownTimer(startTimeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
               String s_remaining = updateTimerText(millisUntilFinished);
               holder.timerTextView.setText("Starts in "+s_remaining);
            }

            @Override
            public void onFinish() {
                holder.timerTextView.setText("Contest Ongoing");
            }
        };
        countDownTimer.start();
    }

    @Override
    public int getItemCount() {
        return codeforcesContests.size();
    }

    public void updateRemainingTime() {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView timeTextView;
        TextView timerTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            timerTextView=itemView.findViewById(R.id.timerTextView);
        }
    }

    private String updateTimerText(long millisUntilFinished) {
        int hours = (int) (millisUntilFinished / 3600000);
        int minutes = (int) ((millisUntilFinished % 3600000) / 60000);
        int seconds = (int) ((millisUntilFinished % 60000) / 1000);

        String timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return timeText;

    }

    public static void convertSeconds(long seconds, int[] time) {
        time[0] = (int) (seconds / 3600); // hours
        seconds %= 3600;
        time[1] = (int) (seconds / 60);    // minutes
        time[2] = (int) (seconds % 60);    // seconds
    }
}
