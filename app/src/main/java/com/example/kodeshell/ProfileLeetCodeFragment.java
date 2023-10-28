package com.example.kodeshell;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProfileLeetCodeFragment extends Fragment {

    RecyclerView submissionRecyclerView;
    SubmissionAdapter submissionAdapter;
    private LeetcodeAPIHelper apiHelper;

    String username;

    public ProfileLeetCodeFragment(String username) {
        this.username = username;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//
        View view = inflater.inflate(R.layout.fragment_profile_leet_code, container, false);

        submissionRecyclerView = view.findViewById(R.id.submissionRecyclerView);
        submissionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        apiHelper = new LeetcodeAPIHelper();


        ArrayList<SubmissionDetails> list = new ArrayList<>();

        String leetcodehandle = username;

        if(!username.isEmpty()) {
            JSONObject variables = new JSONObject();
            try {
                variables.put("username", leetcodehandle);
                variables.put("limit", 20); // Set the limit for recent submissions
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
                            long subtime=Long.parseLong(submissionTimestamp);
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
                            obj1.setName(submissionTitle);
                            obj1.setStatus("AC");

                            obj1.setTime(submissiondate);
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

        }

        return view;
    }



}