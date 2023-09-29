package com.example.kodeshell;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProfileLeetCodeFragment extends Fragment {

    RecyclerView submissionRecyclerView;
    SubmissionAdapter submissionAdapter;
    private LeetcodeAPIHelper apiHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile_leet_code, container, false);

        submissionRecyclerView = view.findViewById(R.id.submissionRecyclerView);
        submissionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        apiHelper = new LeetcodeAPIHelper();


        ArrayList<SubmissionDetails> list = new ArrayList<>();

        String leetcodehandle="neal_wu";

        JSONObject variables = new JSONObject();
        try {
            variables.put("username", "neal_wu");
            variables.put("limit", 15); // Set the limit for recent submissions
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String query = "query recentAcSubmissions($username: String!, $limit: Int!) { " +
                "recentAcSubmissionList(username: $username, limit: $limit) { " +
                "  id " +
                "  title " +
                "  titleSlug " +
                "  timestamp " +
                "} " +
                "}";





        apiHelper.executeQuery(query, variables, new LeetcodeAPIHelper.ContestListCallback() {
            @Override
            public void onSuccess(JSONObject userObject) {
                try {

                    JSONObject data = userObject.getJSONObject("data");
                    JSONArray recentSubmissionsArray = data.getJSONArray("recentAcSubmissionList");

                    for (int i = 0; i < recentSubmissionsArray.length(); i++) {
                        JSONObject submission = recentSubmissionsArray.getJSONObject(i);
                        String submissionId = submission.getString("id");
                        String submissionTitle = submission.getString("title");
                        String submissionTitleSlug = submission.getString("titleSlug");
                        String submissionTimestamp = submission.getString("timestamp");

                        SubmissionDetails obj1 = new SubmissionDetails();
                        obj1.setName(submissionTitle);
                        obj1.setStatus("AC");

                        obj1.setTime(convertTimestampToDate(submissionTimestamp));
                        list.add(obj1);
                    }



                    submissionAdapter = new SubmissionAdapter(list);
                    submissionRecyclerView.setAdapter(submissionAdapter);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Request failed", Toast.LENGTH_SHORT).show();
            }
        });







        return view;
    }

    public static String convertTimestampToDate(String timestamp) {
        try {
            long unixTimestamp = Long.parseLong(timestamp);
            Date date = new Date(unixTimestamp * 1000L); // Convert to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return sdf.format(date);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "Invalid Timestamp";
        }
    }
}