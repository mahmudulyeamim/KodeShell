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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ProfileAtCoderFragment extends Fragment {

    RecyclerView submissionRecyclerView;
    SubmissionAdapter submissionAdapter;
    private AtcoderAPIHelper apiHelper;
    String username;

    public ProfileAtCoderFragment(String username) {
        this.username = username;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_at_coder, container, false);

        submissionRecyclerView = view.findViewById(R.id.ac_submissionRecyclerView);
        submissionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        apiHelper = new AtcoderAPIHelper();

        ArrayList<SubmissionDetails> list = new ArrayList<>();

        String atcoderhandle = username;

        if(!username.isEmpty()) {
            AtcoderAPIHelper.ContestListCallback contestListCallback = new AtcoderAPIHelper.ContestListCallback() {
                @Override
                public void onSuccess(JSONArray usersubmissionhistroyArray) {
                    StringBuilder problemNames = new StringBuilder();
                    int listsz = usersubmissionhistroyArray.length();
                    int lst10 = 10;
                    for (int i = listsz - 1; i >= 0 && lst10 > 0; i--) {
                        try {
                            JSONObject submissionhistroyObject = usersubmissionhistroyArray.getJSONObject(i);
                            String problemid = submissionhistroyObject.getString("problem_id");
                            long subtime = submissionhistroyObject.getInt("epoch_second");
                            String result = submissionhistroyObject.getString("result");
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
                            lst10 -= 1;
                            SubmissionDetails obj1 = new SubmissionDetails();
                            obj1.setName(problemid);
                            obj1.setStatus(result);
                            obj1.setTime(submissiondate);

                            list.add(obj1);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //set

                    submissionAdapter = new SubmissionAdapter(list);
                    submissionRecyclerView.setAdapter(submissionAdapter);

                    // Log.e("recycler_view_height_ac_insider", Integer.toString(submissionRecyclerView.getHeight()));

                }

                @Override
                public void onFailure(Exception e) {
                    // Handle the error scenario
                }
            };
            apiHelper.getAtcodersubmissionHistoryOfUser(contestListCallback, atcoderhandle);
        }

        // Log.e("recycler_view_height_ac", Integer.toString(submissionRecyclerView.getHeight()));

        return view;
    }
}