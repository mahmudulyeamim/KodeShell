package com.example.kodeshell;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class ProfileFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ProfileViewPagerAdapter profileViewPagerAdapter;

    TextView info1, info1Text, info2, info2Text, username, rating;
    ImageView profile_pic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tabLayout = view.findViewById(R.id.profile_tab_layout);
        viewPager2 = view.findViewById(R.id.profile_view_pager);
        profileViewPagerAdapter = new ProfileViewPagerAdapter(getActivity());
        viewPager2.setAdapter(profileViewPagerAdapter);

        info1 = view.findViewById(R.id.profile_info1);
        info1Text = view.findViewById(R.id.profile_info1_text);
        info2 = view.findViewById(R.id.profile_info2);
        info2Text = view.findViewById(R.id.profile_info2_text);
        profile_pic = view.findViewById(R.id.profile_picture);
        username = view.findViewById(R.id.profile_username);
        rating = view.findViewById(R.id.profile_rating_info);

        // default setup
        info1.setText("15");
        info1Text.setText("Posts");
        info2.setText("101");
        info2Text.setText("Followers");
        profile_pic.setImageResource(R.drawable.default_profile_pic);
        username.setText("Rifat Khan");
        rating.setText("Contribution: +10");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        info1.setText("15");
                        info1Text.setText("Posts");
                        info2.setText("101");
                        info2Text.setText("Followers");
                        profile_pic.setImageResource(R.drawable.default_profile_pic);
                        username.setText("Rifat Khan");
                        rating.setText("Contribution: +10");
                        break;
                    case 1:
                        info1.setText("25");
                        info1Text.setText("Problems");
                        info2.setText("101");
                        info2Text.setText("Contests");
                        profile_pic.setImageResource(R.drawable.icon_codeforces);
                        username.setText("CodeForces");
                        rating.setText("Rating: 1400");
                        break;
                    case 2:
                        info1.setText("56");
                        info1Text.setText("Problems");
                        info2.setText("101");
                        info2Text.setText("Contests");
                        profile_pic.setImageResource(R.drawable.icon_atcoder);
                        username.setText("AtCoders");
                        rating.setText("Rating: 1400");
                        break;
                    case 3:
                        info1.setText("256");
                        info1Text.setText("Problems");
                        info2.setText("101");
                        info2Text.setText("Contests");
                        profile_pic.setImageResource(R.drawable.icon_leetcode);
                        username.setText("LeetCode");
                        rating.setText("Rank: 219121");

                }
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        tabLayout.getTabAt(position).select();
                }
                super.onPageSelected(position);
            }
        });

        return view;
    }
}