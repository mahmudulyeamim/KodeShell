package com.example.kodeshell;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileAtCoderFragment extends Fragment {

    RecyclerView submissionRecyclerView;
    SubmissionAdapter submissionAdapter;
    private AtcoderAPIHelper apiHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_at_coder, container, false);

        submissionRecyclerView = view.findViewById(R.id.submissionRecyclerView);
        submissionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        apiHelper = new AtcoderAPIHelper();

        ArrayList<SubmissionDetails> list = new ArrayList<>();

        String atcoderhandle="Istahak_0";
        AtcoderAPIHelper.ContestListCallback contestListCallback = new AtcoderAPIHelper.ContestListCallback() {
            @Override
            public void onSuccess(JSONArray usersubmissionhistroyArray) {
                StringBuilder problemNames = new StringBuilder();
                int listsz=usersubmissionhistroyArray.length();
                int lst10=10;
                for (int i = listsz-1; i >=0 && lst10>0; i--) {
                    try {
                        JSONObject submissionhistroyObject = usersubmissionhistroyArray.getJSONObject(i);
                        String problemid=submissionhistroyObject.getString("problem_id");
                        int point=submissionhistroyObject.getInt("point");
                        String result=submissionhistroyObject.getString("result");
//                        String full=problemid+" "+Integer.toString(point)+" "+result+"\n";
//                        problemNames.append(full);
                        lst10-=1;
                        SubmissionDetails obj1 = new SubmissionDetails();
                        obj1.setName(problemid);
                        obj1.setStatus(result);
                        obj1.setTime(Integer.toString(point));
                        list.add(obj1);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
               //set

                submissionAdapter = new SubmissionAdapter(list);
                submissionRecyclerView.setAdapter(submissionAdapter);

            }

            @Override
            public void onFailure(Exception e) {
                // Handle the error scenario
            }
        };
        apiHelper.getAtcodersubmissionHistoryOfUser(contestListCallback,atcoderhandle);



        return view;
    }
}