package com.example.kodeshell;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private CodeforcesAPIHelper apiHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contest, container, false);

        contestRecyclerView = view.findViewById(R.id.contestRecyclerView);
        contestRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<ContestDetails> list = new ArrayList<>();

        atcoderAPIHelper = new AtcoderAPIHelper();
        apiHelper = new CodeforcesAPIHelper(getContext());


        apiHelper.fetchUpcomingContests(new CodeforcesAPIHelper.UserInfoListener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray contestsArray = response.getJSONArray("result");
                    for (int i = 0; i < contestsArray.length(); i++) {
                        JSONObject contest = contestsArray.getJSONObject(i);
                        String flag = contest.getString("phase");
                        if (flag.equals("BEFORE")) {
                            String contestName = contest.getString("name");
                            long startTimeSeconds = contest.getLong("startTimeSeconds");
                            int durationSeconds = contest.getInt("durationSeconds");
                            ContestDetails ct= new ContestDetails();
                            ct.setContestIcon(R.drawable.icon_codeforces);
                            ct.setContestName(contestName);
                            ct.setSttime(convertsecond(startTimeSeconds));
                            ct.setEdtime(convertsecond(startTimeSeconds+durationSeconds));
                            ct.setUrl("https://codeforces.com");
                            ct.setContestDuration(convertSecondsToHoursMinutes(durationSeconds));
                            String startTime=convertsecond(startTimeSeconds);
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
                            ct.setContestDate(contestDate);
                            ct.setContestTime(contestTime);
                            list.add(0,ct);

                        }

                        contestAdapter = new ContestAdapter(list, getContext());
                        contestRecyclerView.setAdapter(contestAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
            }
        });


        AtcoderAPIHelper.ContestListCallback contestListCallback = new AtcoderAPIHelper.ContestListCallback() {
            @Override
            public void onSuccess(JSONArray contestArray) {
                for (int i = 0; i < contestArray.length(); i++) {
                    try {
                        JSONObject contestObject = contestArray.getJSONObject(i);
                        String title = contestObject.getString("name");
                        String startTime = contestObject.getString("start_time");
                        int durationSeconds = contestObject.getInt("duration");
                        String site = contestObject.getString("site");
                        String url = contestObject.getString("url");
                        String end_time = contestObject.getString("end_time");
                        if (site.equals("CodeChef")) {
                            startTime = convertDateFormat(startTime);
                            end_time = convertDateFormat(end_time);
                            Log.d("hiii", startTime);
                        }

                        if ( site.equals("AtCoder") || site.equals("LeetCode") || site.equals("CodeForces::Gym") || site.equals("CodeChef")) {
                            if (isvalidcontest(end_time)) {
                                ContestDetails contestDetails = new ContestDetails();

                                if (site.equals("CodeForces") || site.equals("CodeForces::Gym"))
                                    contestDetails.setContestIcon(R.drawable.icon_codeforces);
                                else if (site.equals("AtCoder"))
                                    contestDetails.setContestIcon(R.drawable.icon_atcoder);
                                else if (site.equals("CodeChef")) {
                                    contestDetails.setContestIcon(R.drawable.icon_codechef);

                                } else contestDetails.setContestIcon(R.drawable.icon_leetcode);

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
                            public int compare(ContestDetails c1, ContestDetails c2) {
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

    private boolean isvalidcontest(String endtime) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
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

        return (remainingMillis > 0);
    }

    public static String convertDateFormat(String inputDate) {
        String inputFormat = "yyyy-MM-dd HH:mm:ss";
        String outputFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat inputFormatter = new SimpleDateFormat(inputFormat);
        SimpleDateFormat outputFormatter = new SimpleDateFormat(outputFormat);

        // Set the time zone to UTC to match your input
        inputFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date date = inputFormatter.parse(inputDate);
            // Add 6 hours
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, -6);
            return outputFormatter.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "Parsing failed"; // Handle the parsing error gracefully
        }
    }

    public static String convertsecond(long second) {

        long timeInMilliseconds = second * 1000L-6*3600*1000;

        // Create a Date object using the milliseconds
        Date date = new Date(timeInMilliseconds);

        // Create a SimpleDateFormat object for the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        // Format the date
        String formattedDate = sdf.format(date);
        return  formattedDate;

    }
}