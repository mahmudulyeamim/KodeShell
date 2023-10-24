package com.example.kodeshell;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Upcoming_Codeforces_List extends AppCompatActivity {

    private RecyclerView contestRecyclerView;
    private ContestAdapter contestAdapter;
    private List<ContestDetails> Contests;
    private CodeforcesAPIHelper apiHelper;
    private AtcoderAPIHelper atcoderAPIHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_codeforces_list);
        atcoderAPIHelper = new AtcoderAPIHelper();



       apiHelper = new CodeforcesAPIHelper(this);
        contestRecyclerView = findViewById(R.id.recyclerViewContacts);
        Contests = new ArrayList<>(); // Initialize with your contest data

        AtcoderAPIHelper.ContestListCallback contestListCallback = new AtcoderAPIHelper.ContestListCallback() {
            @Override
            public void onSuccess(JSONArray contestArray) {
                for (int i = 0; i < contestArray.length(); i++) {
                    try {
                        JSONObject contestObject = contestArray.getJSONObject(i);
                        String title = contestObject.getString("name");
                        String startTime = contestObject.getString("start_time");
                        int durationSeconds = contestObject.getInt("duration");
                        String site=contestObject.getString("site");
                        long starttimeINsecond=timeconverter(site,startTime);
                        String url=contestObject.getString("url");
                        String endtime=contestObject.getString("end_time");

                     if(site.equals("CodeForces") || site.equals("AtCoder") || site.equals("LeetCode")) {

                        if(isvalidcontest(endtime)) {
                            ContestDetails contestDetails = new ContestDetails();

                            if (site.equals("CodeForces"))
                                contestDetails.setContestIcon(R.drawable.icon_codeforces);
                            else if (site.equals("AtCoder"))
                                contestDetails.setContestIcon(R.drawable.icon_atcoder);
                            else contestDetails.setContestIcon(R.drawable.icon_leetcode);
                            contestDetails.setContestName(title);
                            contestDetails.setContestDate(startTime);
                            contestDetails.setContestTime("yyy");
                            contestDetails.setUrl(url);
                            contestDetails.setContestDuration(Integer.toString(durationSeconds));
                            Contests.add(contestDetails);
                        }
                     }
//
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

//                Collections.sort(codeforcesContests, new Comparator<Codeforces_Contest>() {
//                    @Override
//                    public int compare(Codeforces_Contest contest1, Codeforces_Contest contest2) {
//                        return Long.compare(contest1.getStartTimeSeconds(), contest2.getStartTimeSeconds());
//                    }
//                });

//                activateAdapter();
            }

            @Override
            public void onFailure(Exception e) {
                // Handle the error scenario
            }
        };
        atcoderAPIHelper.getUpcomingContests(contestListCallback);

      /*  apiHelper.fetchUpcomingContests(new CodeforcesAPIHelper.UserInfoListener() {
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
        });*/


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        contestRecyclerView.setLayoutManager(layoutManager);
        //contestRecyclerView.setAdapter(codeforcesContestAdapter);


    }
//    private void activateAdapter(){
//        codeforcesContestAdapter = new Codeforces_ContestAdapter(this, codeforcesContests);
//        contestRecyclerView.setAdapter(codeforcesContestAdapter);
//    }
    private long timeconverter(String site,String utcTimestamp){
        if(site.equals("CodeChef")) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            long seconds=0;
            try {
                Date date = inputFormat.parse(utcTimestamp);
                seconds = date.getTime() / 1000;


            } catch (ParseException e) {
                e.printStackTrace();
            }

            return seconds;
        }else{
            Instant instant = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                instant = Instant.parse(utcTimestamp);
            }
            long seconds=0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                seconds = instant.getEpochSecond();
            }
            return  seconds;
        }
    }

    private boolean isvalidcontest(String endtime){

         SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        Date targetDate = null;
        try {
            targetDate = dateFormat.parse(endtime);
            // Add 6 hours to the target date

            targetDate.setTime(targetDate.getTime() + 6 * 60 * 60 * 1000);
        } catch (android.net.ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        long currentTimeMillis = System.currentTimeMillis();
        long targetTimeMillis = targetDate.getTime();
        long remainingMillis = targetTimeMillis - currentTimeMillis;

        return (remainingMillis>0);
    }

}