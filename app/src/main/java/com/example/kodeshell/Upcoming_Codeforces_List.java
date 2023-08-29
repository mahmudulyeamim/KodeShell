package com.example.kodeshell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Upcoming_Codeforces_List extends AppCompatActivity {

    private RecyclerView contestRecyclerView;
    private Codeforces_ContestAdapter codeforcesContestAdapter;
    private List<Codeforces_Contest> codeforcesContests;
    private CodeforcesAPIHelper apiHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_codeforces_list);
        apiHelper = new CodeforcesAPIHelper(this);
        contestRecyclerView = findViewById(R.id.recyclerViewContacts);
        codeforcesContests = new ArrayList<>(); // Initialize with your contest data
        apiHelper.fetchUpcomingContests(new CodeforcesAPIHelper.UserInfoListener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray contestsArray = response.getJSONArray("result");
                    for (int i = 0; i < contestsArray.length(); i++) {
                        JSONObject contest = contestsArray.getJSONObject(i);
                        String flag= contest.getString("phase");
                        if(flag.equals("BEFORE"))  {
                            String contestName = contest.getString("name");
                            long startTimeSeconds = contest.getLong("startTimeSeconds");
                            int durationSeconds = contest.getInt("durationSeconds");
                            // Process the contest details
                            Codeforces_Contest a = new Codeforces_Contest(contestName, startTimeSeconds, durationSeconds);

                            codeforcesContests.add(a);
                        }
                    }
                    Collections.sort(codeforcesContests, new Comparator<Codeforces_Contest>() {
                        @Override
                        public int compare(Codeforces_Contest codeforcesContest1, Codeforces_Contest codeforcesContest2) {
                            return Long.compare(codeforcesContest1.getStartTimeSeconds(), codeforcesContest2.getStartTimeSeconds());
                        }
                    });
                    activateAdapter();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        contestRecyclerView.setLayoutManager(layoutManager);
        contestRecyclerView.setAdapter(codeforcesContestAdapter);


    }
    private void activateAdapter(){
        codeforcesContestAdapter = new Codeforces_ContestAdapter(this, codeforcesContests);
        contestRecyclerView.setAdapter(codeforcesContestAdapter);
    }

}