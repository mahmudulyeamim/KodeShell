package com.example.kodeshell;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ProfileViewPagerAdapter profileViewPagerAdapter;

    TextView info1, info1Text, info2, info2Text, username, rating;
    ImageView profile_pic;
    private CodeforcesAPIHelper apiHelper;

    private AtcoderAPIHelper atcoderAPIHelper;
    private LeetcodeAPIHelper leetcodeAPIHelper;

    ImageView openNavButton;

    DrawerLayout drawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tabLayout = view.findViewById(R.id.profile_tab_layout);
        viewPager2 = view.findViewById(R.id.profile_view_pager);
        profileViewPagerAdapter = new ProfileViewPagerAdapter(getActivity());
        atcoderAPIHelper = new AtcoderAPIHelper();
        viewPager2.setAdapter(profileViewPagerAdapter);

        info1 = view.findViewById(R.id.profile_info1);
        info1Text = view.findViewById(R.id.profile_info1_text);
        info2 = view.findViewById(R.id.profile_info2);
        info2Text = view.findViewById(R.id.profile_info2_text);
        profile_pic = view.findViewById(R.id.profile_picture);
        username = view.findViewById(R.id.profile_username);
        rating = view.findViewById(R.id.profile_rating_info);
        apiHelper = new CodeforcesAPIHelper(getContext());
        leetcodeAPIHelper=new LeetcodeAPIHelper();

        // default setup
        info1.setText("15");
        info1Text.setText("Posts");
        info2.setText("+121");
        info2Text.setText("Contribution");
        Picasso.get().load(R.drawable.avatar1).fit().centerInside().into(profile_pic);
        username.setText("Rifat Khan");
        rating.setText("");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition()==0) {
                    info1.setText("15");
                    info1Text.setText("Posts");
                    info2.setText("+121");
                    info2Text.setText("Contribution");
                    Picasso.get().load(R.drawable.avatar1).fit().centerInside().into(profile_pic);
                    username.setText("Rifat Khan");
                    rating.setText("");
                }
                else if(tab.getPosition() == 1) {
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


                                info1.setText(Integer.toString(cfrating));
                                info1Text.setText("Rating");
                                info2.setText(Integer.toString(mxcfrating));
                                info2Text.setText("MaxRating");
                                username.setText(handle);
                                rating.setText(rank);
                                Picasso.get().load(imageUrl).into(profile_pic);



                            } catch (JSONException e) {

                                info1.setText("0");
                                info1Text.setText("Rating");
                                info2.setText("0");
                                info2Text.setText("MaxRating");
                                username.setText("Invalid Username");
                                rating.setText("");
                                Picasso.get().load(R.drawable.icon_codeforces).into(profile_pic);

                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(String errorMessage) {
                            // Handle error
                            info1.setText("0");
                            info1Text.setText("Rating");
                            info2.setText("0");
                            info2Text.setText("MaxRating");
                            username.setText("Invalid Username");
                            rating.setText("");
                            Picasso.get().load(R.drawable.icon_codeforces).into(profile_pic);

                        }
                    });

                }
                else if(tab.getPosition()==2) {
                    String atcoderhandle="Istahak_0";
                    AtcoderAPIHelper.ContestListCallback contestListCallback = new AtcoderAPIHelper.ContestListCallback() {
                        @Override
                        public void onSuccess(JSONArray usercontesthistroyArray) {
                            int atrating=0,mxrating=0;
                            for (int i = 0; i < usercontesthistroyArray.length(); i++) {
                                try {
                                    JSONObject contesthistroyObject = usercontesthistroyArray.getJSONObject(i);
                                    atrating=contesthistroyObject.getInt("NewRating");
                                    mxrating=Math.max(mxrating,atrating);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            info1.setText(Integer.toString(atrating));
                            info1Text.setText("Rating");
                            info2.setText(Integer.toString(mxrating));
                            info2Text.setText("MaxRating");
                            Picasso.get().load(R.drawable.icon_atcoder).into(profile_pic);
                            // profile_pic.setImageResource(R.drawable.icon_atcoder);
                            username.setText(atcoderhandle);
                            rating.setText(getRank(atrating));
                        }

                        @Override
                        public void onFailure(Exception e) {
                            // Handle the error scenario
                            info1.setText("0");
                            info1Text.setText("Rating");
                            info2.setText("0");
                            info2Text.setText("MaxRating");
                            username.setText("Invalid Username");
                            rating.setText("");
                            Picasso.get().load(R.drawable.icon_atcoder).into(profile_pic);

                        }
                    };

                    atcoderAPIHelper.getAtcoderContestHistoryOfUser(contestListCallback,atcoderhandle);

                }
                else {
                    String leetcodeHandle="neal_wu";
                    JSONObject variables = new JSONObject();
                    try {
                        variables.put("username", leetcodeHandle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String query = "query userContestRankingInfo($username: String!) { " +
                            "userContestRanking(username: $username) { " +
                            "  attendedContestsCount " +
                            "  rating " +
                            "  globalRanking " +
                            "  totalParticipants " +
                            "  topPercentage " +
                            "  badge { " +
                            "    name " +
                            "  } " +
                            "} " +
                            "}";

                    leetcodeAPIHelper.executeQuery(query, variables, new LeetcodeAPIHelper.ContestListCallback() {
                        @Override
                        public void onSuccess(JSONObject userObject) {
                            try {
                                JSONObject data = userObject.getJSONObject("data");
                                JSONObject userContestRanking = data.getJSONObject("userContestRanking");

                                int attendedContestsCount = userContestRanking.getInt("attendedContestsCount");
                                int leetcoderating = (int)userContestRanking.getDouble("rating");
                                int globalRanking = userContestRanking.getInt("globalRanking");
                                int totalParticipants = userContestRanking.getInt("totalParticipants");
                                double topPercentage = userContestRanking.getDouble("topPercentage");
                                JSONObject badge = userContestRanking.getJSONObject("badge");
                                String badgeName = badge.getString("name");


                                info1.setText(Integer.toString(leetcoderating));
                                info1Text.setText("Rating");
                                info2.setText(Integer.toString(attendedContestsCount));
                                info2Text.setText("Contests");
                                Picasso.get().load(R.drawable.icon_leetcode).into(profile_pic);
                                // profile_pic.setImageResource(R.drawable.icon_leetcode);
                                username.setText(leetcodeHandle);

                                rating.setText("Global Rank : " + globalRanking);


                            } catch (Exception e) {

                                info1.setText("0");
                                info1Text.setText("Rating");
                                info2.setText("0");
                                info2Text.setText("Contests");
                                Picasso.get().load(R.drawable.icon_leetcode).into(profile_pic);
                                // profile_pic.setImageResource(R.drawable.icon_leetcode);
                                username.setText(leetcodeHandle);

                                rating.setText("Not participated in any contest");

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

        drawerLayout = view.findViewById(R.id.profile_drawer_layout);
        NavigationView navigationView = view.findViewById(R.id.nav_drawer_view);
        navigationView.setNavigationItemSelectedListener(this);

        openNavButton = view.findViewById(R.id.open_nav_button);

        openNavButton.setOnClickListener(view1 -> openNavigationView());

        return view;
    }

    private void openNavigationView() {
        drawerLayout.openDrawer(GravityCompat.END);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.edit_profile_button) {
            EditProfileFragment editProfileFragment = new EditProfileFragment();

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.main_fragment_container, editProfileFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(item.getItemId() == R.id.about_us_button) {
            AboutUsFragment aboutUsFragment = new AboutUsFragment();

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.main_fragment_container, aboutUsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else {
            Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
        }

        item.setChecked(false);
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }


    private static String getRank(int rating) {
        if (rating >= 2800 && rating <= 3199) {
            return "Red";
        } else if (rating >= 2400 && rating <= 2799) {
            return "Orange";
        } else if (rating >= 2000 && rating <= 2399) {
            return "Yellow";
        } else if (rating >= 1600 && rating <= 1999) {
            return "Blue";
        } else if (rating >= 1200 && rating <= 1599) {
            return "Cyan";
        } else if (rating >= 800 && rating <= 1199) {
            return "Green";
        } else if (rating >= 400 && rating <= 799) {
            return "Brown";
        } else {
            return "Gray";
        }
    }
}