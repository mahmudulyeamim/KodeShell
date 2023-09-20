package com.example.kodeshell;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class ContestFragment extends Fragment {

    RecyclerView contestRecyclerView;
    ContestAdapter contestAdapter;
    private AtcoderAPIHelper atcoderAPIHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contest, container, false);

        contestRecyclerView = view.findViewById(R.id.contestRecyclerView);
        contestRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<ContestDetails> list = new ArrayList<>();

        atcoderAPIHelper = new AtcoderAPIHelper();


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

                        if(site.equals("CodeForces") || site.equals("AtCoder") || site.equals("LeetCode")) {
                            ContestDetails contestDetails = new ContestDetails();

                            if(site.equals("CodeForces"))
                                contestDetails.setContestIcon(R.drawable.icon_codeforces);
                            else if(site.equals("AtCoder"))
                                contestDetails.setContestIcon(R.drawable.icon_atcoder);
                            else contestDetails.setContestIcon(R.drawable.icon_leetcode);
                            contestDetails.setContestName(title);
                            contestDetails.setContestDate(startTime);
                            contestDetails.setContestTime("yyy");
                            contestDetails.setUrl(url);
                            contestDetails.setContestDuration(Integer.toString(durationSeconds));
                            list.add(contestDetails);
                        }


                        contestAdapter = new ContestAdapter(list, getContext());
                        contestRecyclerView.setAdapter(contestAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Exception e) {
                // Handle the error scenario
            }
        };
        atcoderAPIHelper.getUpcomingContests(contestListCallback);




        return view;
    }


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
}