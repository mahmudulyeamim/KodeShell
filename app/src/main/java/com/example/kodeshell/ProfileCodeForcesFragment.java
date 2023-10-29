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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProfileCodeForcesFragment extends Fragment {

    RecyclerView submissionRecyclerView;
    SubmissionAdapter submissionAdapter;
    private CodeforcesAPIHelper apiHelper;

    String username;

    public ProfileCodeForcesFragment(String username) {
        this.username = username;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_code_forces, container, false);

        submissionRecyclerView = view.findViewById(R.id.cf_submissionRecyclerView);
        submissionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        apiHelper = new CodeforcesAPIHelper(getContext());

        ArrayList<SubmissionDetails> list = new ArrayList<>();



        if(username != null && !username.isEmpty()) {
            apiHelper.getUserLastSubmissions(username, 10, new CodeforcesAPIHelper.UserInfoListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        StringBuilder problemNames = new StringBuilder();
                        JSONArray submissions = response.getJSONArray("result");
                        for (int i = 0; i < submissions.length(); i++) {
                            JSONObject submission = submissions.getJSONObject(i);
                            long subtime=submission.getLong("creationTimeSeconds");
                            JSONObject problem = submission.getJSONObject("problem");
                            String problemName = problem.getString("name");

                            String tags = problem.getString("tags");

                            String verdict1 = submission.getString("verdict");
                            int timeConsumedMillis = submission.getInt("timeConsumedMillis");
                            subtime=subtime+6*3600;
                            LocalDateTime localDateTime=null;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(subtime), ZoneId.of("UTC"));
                            }
                            DateTimeFormatter formatter = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                formatter = DateTimeFormatter.ofPattern("dd MMM yyyy 'at' HH:mm:ss");
                            }
                            String submissiondate=null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                submissiondate = localDateTime.format(formatter);
                            }
                            SubmissionDetails obj1 = new SubmissionDetails();
                            obj1.setName(problemName);
                            obj1.setStatus(verdict1);
                            obj1.setTime(submissiondate);
                            list.add(obj1);


                        }


                        submissionAdapter = new SubmissionAdapter(list);
                        submissionRecyclerView.setAdapter(submissionAdapter);

                        // Log.e("recycler_view_height_cf_insider", Integer.toString(submissionRecyclerView.getHeight()));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    // Handle error

                }
            });
        }

        // Log.e("recycler_view_height_cf", Integer.toString(submissionRecyclerView.getHeight()));

       // submissionAdapter = new SubmissionAdapter(list);
       // submissionRecyclerView.setAdapter(submissionAdapter);

        return view;
    }


}