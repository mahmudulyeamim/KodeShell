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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
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
                        String url=contestObject.getString("url");
                        String end_time=contestObject.getString("end_time");
                        if(site.equals("CodeForces") || site.equals("AtCoder") || site.equals("LeetCode")) {
                            if(isvalidcontest(end_time))
                            {  ContestDetails contestDetails = new ContestDetails();

                            if (site.equals("CodeForces"))
                                contestDetails.setContestIcon(R.drawable.icon_codeforces);
                            else if (site.equals("AtCoder"))
                                contestDetails.setContestIcon(R.drawable.icon_atcoder);
                            else contestDetails.setContestIcon(R.drawable.icon_leetcode);

                            // Define the input date format
                            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
                            inputDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
                            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                            SimpleDateFormat outputTimeFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

                            String contestDate = "16 sep";
                            String contestTime = "8:00";
                            try {
                                // Parse the input date string
                                Date date = inputDateFormat.parse(startTime);

                                // Create a Calendar instance and add 6 hours
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                calendar.add(Calendar.HOUR_OF_DAY, 6);

                                // Get the modified Date object
                                Date modifiedDate = calendar.getTime();
                                // Format date and time components
                                contestDate = outputDateFormat.format(modifiedDate);
                                contestTime = outputTimeFormat.format(modifiedDate);


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String durationinhoursandmin = convertSecondsToHoursMinutes(durationSeconds);
                            contestDetails.setSite(site);

                            contestDetails.setContestName(title);
                            contestDetails.setContestDate(contestDate);
                            contestDetails.setContestTime(contestTime);
                            contestDetails.setUrl(url);
                            contestDetails.setContestDuration(durationinhoursandmin);
                            contestDetails.setSttime(startTime);
                            contestDetails.setEdtime(end_time);
                            contestDetails.setIsalarm(false);
                            list.add(contestDetails);
                        }
                        }

                        Collections.sort(list, new Comparator<ContestDetails>() {
                            @Override
                            public int compare(ContestDetails c1,ContestDetails c2) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);

                                try {
                                    Date date1 = dateFormat.parse(c1.getContestDate());
                                    Date date2 = dateFormat.parse(c2.getContestDate());
                                    Date time1 = timeFormat.parse(c1.getContestTime());
                                    Date time2 = timeFormat.parse(c2.getContestTime());

                                    // Compare first by date and then by time
                                    if (date1.equals(date2)) {
                                        return time1.compareTo(time2);
                                    } else {
                                        return date1.compareTo(date2);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    return 0;
                                }
                            }
                        });


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


    public static String convertSecondsToHoursMinutes(int seconds) {
        int hours = seconds / 3600;
        int remainingSeconds = seconds % 3600;
        int minutes = remainingSeconds / 60;

        // Use DecimalFormat to ensure two digits for hours and minutes
        DecimalFormat df = new DecimalFormat("00");
        String formattedHours = df.format(hours);
        String formattedMinutes = df.format(minutes);

        return formattedHours + ":" + formattedMinutes;
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