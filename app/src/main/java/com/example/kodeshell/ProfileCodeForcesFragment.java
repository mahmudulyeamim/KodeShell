package com.example.kodeshell;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        submissionRecyclerView = view.findViewById(R.id.submissionRecyclerView);
        submissionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        apiHelper = new CodeforcesAPIHelper(getContext());

        ArrayList<SubmissionDetails> list = new ArrayList<>();



        if(!username.isEmpty()) {
            apiHelper.getUserLastSubmissions(username, 10, new CodeforcesAPIHelper.UserInfoListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        StringBuilder problemNames = new StringBuilder();
                        JSONArray submissions = response.getJSONArray("result");
                        for (int i = 0; i < submissions.length(); i++) {
                            JSONObject submission = submissions.getJSONObject(i);
                            JSONObject problem = submission.getJSONObject("problem");
                            String problemName = problem.getString("name");

                            String tags = problem.getString("tags");

                            String verdict1 = submission.getString("verdict");
                            int timeConsumedMillis = submission.getInt("timeConsumedMillis");

                            SubmissionDetails obj1 = new SubmissionDetails();
                            obj1.setName(problemName);
                            obj1.setStatus(verdict1);
                            obj1.setTime(timeConsumedMillis + " ms");
                            list.add(obj1);


                        }


                        submissionAdapter = new SubmissionAdapter(list);
                        submissionRecyclerView.setAdapter(submissionAdapter);


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

       // submissionAdapter = new SubmissionAdapter(list);
       // submissionRecyclerView.setAdapter(submissionAdapter);

        return view;
    }
}