package com.example.kodeshell;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ContestFragment extends Fragment {

    RecyclerView contestRecyclerView;
    ContestAdapter contestAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contest, container, false);

        contestRecyclerView = view.findViewById(R.id.contestRecyclerView);
        contestRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        contestAdapter = new ContestAdapter(getContestList(), getContext());
        contestRecyclerView.setAdapter(contestAdapter);



        return view;
    }

    private ArrayList<ContestDetails> getContestList() {
        // logic for fetching data of contests
        ArrayList<ContestDetails> list = new ArrayList<>();

        ContestDetails contestDetails = new ContestDetails();
        contestDetails.setContestIcon(R.drawable.icon_leetcode);
        contestDetails.setContestName("Leetcode BiWeekly 271 Short Contest");
        contestDetails.setContestDate("Sep 18");
        contestDetails.setContestTime("8:35 am");
        contestDetails.setContestDuration("2.15 Hours");
        list.add(contestDetails);

        ContestDetails contestDetails1 = new ContestDetails();
        contestDetails1.setContestIcon(R.drawable.icon_codeforces);
        contestDetails1.setContestName("Codeforces Round 898 Powered by CodeTon");
        contestDetails1.setContestDate("Sep 18");
        contestDetails1.setContestTime("8:35 pm");
        contestDetails1.setContestDuration("2.15 Hours");
        list.add(contestDetails1);

        ContestDetails contestDetails2 = new ContestDetails();
        contestDetails2.setContestIcon(R.drawable.icon_atcoder);
        contestDetails2.setContestName("Atcoder ABC 311");
        contestDetails2.setContestDate("Sep 21");
        contestDetails2.setContestTime("6:00 pm");
        contestDetails2.setContestDuration("2.15 Hours");
        list.add(contestDetails2);

        ContestDetails contestDetails3 = new ContestDetails();
        contestDetails3.setContestIcon(R.drawable.icon_leetcode);
        contestDetails3.setContestName("Leetcode BiWeekly 271");
        contestDetails3.setContestDate("Sep 18");
        contestDetails3.setContestTime("8:35 am");
        contestDetails3.setContestDuration("2.15 Hours");
        list.add(contestDetails3);

        ContestDetails contestDetails4 = new ContestDetails();
        contestDetails4.setContestIcon(R.drawable.icon_codeforces);
        contestDetails4.setContestName("Codeforces Round 898");
        contestDetails4.setContestDate("Sep 18");
        contestDetails4.setContestTime("8:35 pm");
        contestDetails4.setContestDuration("2.15 Hours");
        list.add(contestDetails4);

        ContestDetails contestDetails5 = new ContestDetails();
        contestDetails5.setContestIcon(R.drawable.icon_atcoder);
        contestDetails5.setContestName("Atcoder ABC 311");
        contestDetails5.setContestDate("Sep 21");
        contestDetails5.setContestTime("6:00 pm");
        contestDetails5.setContestDuration("2.15 Hours");
        list.add(contestDetails5);

        ContestDetails contestDetails6 = new ContestDetails();
        contestDetails6.setContestIcon(R.drawable.icon_codeforces);
        contestDetails6.setContestName("Codeforces Round 898");
        contestDetails6.setContestDate("Sep 18");
        contestDetails6.setContestTime("8:35 pm");
        contestDetails6.setContestDuration("Duration: 2.15 Hours");
        list.add(contestDetails6);

        ContestDetails contestDetails7 = new ContestDetails();
        contestDetails7.setContestIcon(R.drawable.icon_atcoder);
        contestDetails7.setContestName("Atcoder ABC 311");
        contestDetails7.setContestDate("Sep 21");
        contestDetails7.setContestTime("6:00 pm");
        contestDetails7.setContestDuration("2.15 Hours");
        list.add(contestDetails7);

        ContestDetails contestDetails8 = new ContestDetails();
        contestDetails8.setContestIcon(R.drawable.icon_atcoder);
        contestDetails8.setContestName("Atcoder ABC 311");
        contestDetails8.setContestDate("Sep 21");
        contestDetails8.setContestTime("6:00 pm");
        contestDetails8.setContestDuration("2.15 Hours");
        list.add(contestDetails8);

        return list;
    }
}