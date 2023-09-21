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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ProfileViewPagerAdapter profileViewPagerAdapter;

    TextView info1, info1Text, info2, info2Text, username, rating;
    ImageView profile_pic;
    private CodeforcesAPIHelper apiHelper;

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
        apiHelper = new CodeforcesAPIHelper(getContext());
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

                    if(tab.getPosition()==0) {
                        info1.setText("15");
                        info1Text.setText("Posts");
                        info2.setText("101");
                        info2Text.setText("Followers");
                        profile_pic.setImageResource(R.drawable.default_profile_pic);
                        username.setText("Rifat Khan");
                        rating.setText("Contribution: +10");
                    }
                    else if(tab.getPosition()==1) {


                       apiHelper.getUserInfo("_0istahak", new CodeforcesAPIHelper.UserInfoListener() {
                            @Override
                            public void onSuccess(JSONObject response) {
                                try {
                                    JSONObject user = response.getJSONArray("result").getJSONObject(0);
                                    String handle = user.getString("handle");
                                    int cfrating = user.getInt("rating");
                                    int mxcfrating = user.getInt("maxRating");
                                    String rank=user.getString("rank");

                                    String imageUrl = user.getString("titlePhoto");

//
////                                    runOnUiThread(() -> {
//                                        info1.setText("25");
//                                        info1Text.setText("Problems");
//                                        info2.setText("101");
//                                        info2Text.setText("Contests");
////                                    profile_pic.setImageResource(R.drawable.icon_codeforces);
//                                        username.setText(handle);
//                                        rating.setText(cfrating);
////                                        Picasso.get().load(imageUrl).into(profile_pic);
////                                    });

                                    info1.setText(Integer.toString(cfrating));
                                    info1Text.setText("Rating");
                                    info2.setText(Integer.toString(mxcfrating));
                                    info2Text.setText("MaxRating");
//                                    profile_pic.setImageResource(R.drawable.icon_codeforces);
                                    username.setText(handle);
                                    rating.setText(rank);
                                    Picasso.get().load(imageUrl).into(profile_pic);



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(String errorMessage) {
                                // Handle error
                            }
                        });




//                        info1.setText("25");
//                        info1Text.setText("Problems");
//                        info2.setText("101");
//                        info2Text.setText("Contests");
//                        profile_pic.setImageResource(R.drawable.icon_codeforces);
//                        username.setText("CodeForces");
//                        rating.setText("Rating: 1400");
                    }
                   else if(tab.getPosition()==2) {
                        info1.setText("56");
                        info1Text.setText("Problems");
                        info2.setText("101");
                        info2Text.setText("Contests");
                        profile_pic.setImageResource(R.drawable.icon_atcoder);
                        username.setText("AtCoders");
                        rating.setText("Rating: 1400");
                    }
                    else {
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