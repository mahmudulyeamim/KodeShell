package com.example.kodeshell;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ProfileCodeForcesFragment extends Fragment {

    RecyclerView submissionRecyclerView;
    SubmissionAdapter submissionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_code_forces, container, false);

        submissionRecyclerView = view.findViewById(R.id.submissionRecyclerView);
        submissionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<SubmissionDetails> list = new ArrayList<>();

        SubmissionDetails obj1 = new SubmissionDetails();
        obj1.setName("C. Salyg1n and the MEX Game");
        obj1.setStatus("Accepted");
        obj1.setTime("21 Sep 2023");
        list.add(obj1);

        SubmissionDetails obj2 = new SubmissionDetails();
        obj2.setName("D. Salyg1n and the MEX Game");
        obj2.setStatus("Accepted");
        obj2.setTime("21 Sep 2023");
        list.add(obj2);

        SubmissionDetails obj3 = new SubmissionDetails();
        obj3.setName("E. Salyg1n and the MEX Game");
        obj3.setStatus("Accepted");
        obj3.setTime("21 Sep 2023");
        list.add(obj3);

        SubmissionDetails obj4 = new SubmissionDetails();
        obj4.setName("F. Salyg1n and the MEX Game");
        obj4.setStatus("Accepted");
        obj4.setTime("21 Sep 2023");
        list.add(obj4);

        SubmissionDetails obj5 = new SubmissionDetails();
        obj5.setName("E. Salyg1n and the MEX Game");
        obj5.setStatus("Accepted");
        obj5.setTime("21 Sep 2023");
        list.add(obj5);

        SubmissionDetails obj6 = new SubmissionDetails();
        obj6.setName("F. Salyg1n and the MEX Game");
        obj6.setStatus("Accepted");
        obj6.setTime("21 Sep 2023");
        list.add(obj6);

        submissionAdapter = new SubmissionAdapter(list);
        submissionRecyclerView.setAdapter(submissionAdapter);

        return view;
    }
}