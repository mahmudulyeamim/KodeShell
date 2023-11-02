package com.example.kodeshell;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    private User user = new User();

    private String currentUserId;

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

//        if(user != null && user.getCodeforcesuname() != null) {
//            // Toast.makeText(getContext(), user.getCodeforcesuname(), Toast.LENGTH_SHORT).show();
//            Log.e("profile", user.getCodeforcesuname());
//        }
//        else {
//            Log.e("profile", "user is null");
//        }

        tabLayout = view.findViewById(R.id.profile_tab_layout);
        viewPager2 = view.findViewById(R.id.profile_view_pager);
        profileViewPagerAdapter = new ProfileViewPagerAdapter(getActivity(), currentUserId);
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

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("user");


        // loadCurrentUserInformation();

        default_setup();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == 0) {
                    info1.setText(String.valueOf(user.postcount));
                    info1Text.setText("Posts");
                    info2.setText(String.valueOf(user.contribution));
                    info2Text.setText("Contribution");
                    loadImage(user.getAvatarid());
                    username.setText(user.firstName+" "+user.lastName);
                    rating.setText("");
                }
                else if(tab.getPosition() == 1) {

                    if(user.codeforcesuname != null && !user.codeforcesuname.isEmpty()) {
                        apiHelper.getUserInfo(user.codeforcesuname, new CodeforcesAPIHelper.UserInfoListener() {
                            @Override
                            public void onSuccess(JSONObject response) {
                                try {
                                    JSONObject user = response.getJSONArray("result").getJSONObject(0);
                                    String handle = user.getString("handle");
                                    int cfrating = user.getInt("rating");
                                    int mxcfrating = user.getInt("maxRating");
                                    String rank = user.getString("rank");

                                    String imageUrl = user.getString("titlePhoto");


                                    info1.setText(Integer.toString(cfrating));
                                    info1Text.setText("Rating");
                                    info2.setText(Integer.toString(mxcfrating));
                                    info2Text.setText("MaxRating");
                                    username.setText(handle);
                                    rating.setText(rank);
                                    Picasso.get().load(imageUrl).into(profile_pic);


                                } catch (JSONException e) {

                                    info1.setText("N/A");
                                    info1Text.setText("Rating");
                                    info2.setText("N/A");
                                    info2Text.setText("MaxRating");
                                    username.setText(user.codeforcesuname);
                                    rating.setText("Didn't participate in any contest");
                                    Picasso.get().load(R.drawable.icon_codeforces).into(profile_pic);

                                    // Toast.makeText(getContext(), "catch", Toast.LENGTH_SHORT).show();

                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(String errorMessage) {
                                // Handle error
                                info1.setText("N/A");
                                info1Text.setText("Rating");
                                info2.setText("N/A");
                                info2Text.setText("MaxRating");
                                username.setText(user.codeforcesuname);
                                rating.setText("Invalid Username");
                                Picasso.get().load(R.drawable.icon_codeforces).into(profile_pic);

                                // Toast.makeText(getContext(), "on error", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else {
                        info1.setText("N/A");
                        info1Text.setText("Rating");
                        info2.setText("N/A");
                        info2Text.setText("MaxRating");
                        username.setText("Username isn't added yet");
                        rating.setText("");
                        Picasso.get().load(R.drawable.icon_codeforces).into(profile_pic);
                    }

                }
                else if(tab.getPosition() == 2) {

                    if(user.atcoderuname != null && !user.atcoderuname.isEmpty()) {

                        String atcoderhandle = user.atcoderuname;
                        AtcoderAPIHelper.ContestListCallback contestListCallback = new AtcoderAPIHelper.ContestListCallback() {
                            @Override
                            public void onSuccess(JSONArray usercontesthistroyArray) {
                                int atrating = 0, mxrating = 0;

                                for (int i = 0; i < usercontesthistroyArray.length(); i++) {
                                    try {
                                        JSONObject contesthistroyObject = usercontesthistroyArray.getJSONObject(i);
                                        atrating = contesthistroyObject.getInt("NewRating");
                                        mxrating = Math.max(mxrating, atrating);

                                    } catch (JSONException e) {

                                        info1.setText("N/A");
                                        info1Text.setText("Rating");
                                        info2.setText("N/A");
                                        info2Text.setText("MaxRating");
                                        username.setText(user.atcoderuname);
                                        rating.setText("Didn't participate in any contest");
                                        Picasso.get().load(R.drawable.icon_atcoder).into(profile_pic);
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
                                info1.setText("N/A");
                                info1Text.setText("Rating");
                                info2.setText("N/A");
                                info2Text.setText("MaxRating");
                                username.setText(user.atcoderuname);
                                rating.setText("Invalid Username");
                                Picasso.get().load(R.drawable.icon_atcoder).into(profile_pic);

                            }
                        };

                        atcoderAPIHelper.getAtcoderContestHistoryOfUser(contestListCallback, atcoderhandle);
                    }

                    else {
                        info1.setText("N/A");
                        info1Text.setText("Rating");
                        info2.setText("N/A");
                        info2Text.setText("MaxRating");
                        username.setText("Username isn't added yet");
                        rating.setText("");
                        Picasso.get().load(R.drawable.icon_atcoder).into(profile_pic);
                    }

                }
                else {
                    if((user.leetcodeuname != null && !user.leetcodeuname.isEmpty())) {

                        String leetcodeHandle = user.leetcodeuname;
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
                                    int leetcoderating = (int) userContestRanking.getDouble("rating");
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

                                    info1.setText("N/A");
                                    info1Text.setText("Rating");
                                    info2.setText("N/A");
                                    info2Text.setText("Contests");
                                    Picasso.get().load(R.drawable.icon_leetcode).into(profile_pic);
                                    // profile_pic.setImageResource(R.drawable.icon_leetcode);
                                    username.setText(leetcodeHandle);

                                    rating.setText("Didn't participate in any contest");

                                    // e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Exception e) {
                                info1.setText("N/A");
                                info1Text.setText("Rating");
                                info2.setText("N/A");
                                info2Text.setText("Contests");
                                Picasso.get().load(R.drawable.icon_leetcode).into(profile_pic);
                                // profile_pic.setImageResource(R.drawable.icon_leetcode);
                                username.setText(user.leetcodeuname);

                                rating.setText("Invalid username");
//                                e.printStackTrace();
//                                Toast.makeText(getContext(), "Request failed", Toast.LENGTH_SHORT).show();
                            }

                        });

                    }
                    else {
                        info1.setText("N/A");
                        info1Text.setText("Rating");
                        info2.setText("N/A");
                        info2Text.setText("Contests");
                        Picasso.get().load(R.drawable.icon_leetcode).into(profile_pic);
                        // profile_pic.setImageResource(R.drawable.icon_leetcode);
                        username.setText("Username isn't added yet");
                        rating.setText("");
                    }
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
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefsFile", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean("hasLoggedIn", false);
            editor.commit();

            Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }

        item.setChecked(false);
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }

    private void loadCurrentUserInformation() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            DatabaseReference currentUserRef = reference.child(userId);
            currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User currentUser = dataSnapshot.getValue(User.class);
                        if (currentUser != null) {
                            user.setAvatarid(currentUser.getAvatarid());
                            user.setFirstName(currentUser.getFirstName());
                            user.setLastName(currentUser.getLastName());
                            user.setAtcoderuname(currentUser.getAtcoderuname());
                            user.setCodeforcesuname(currentUser.getCodeforcesuname());
                            user.setLeetcodeuname(currentUser.getLeetcodeuname());
//                            Toast.makeText(getContext(), String.valueOf(currentUser.getPostcount()), Toast.LENGTH_SHORT).show();
                            user.setPostcount(currentUser.getPostcount());
                            user.setContribution(currentUser.getContribution());
                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }
    private void loadImage(int i){
        if(i == 0) {
            Picasso.get().load(R.drawable.avatar1).fit().centerInside().into(profile_pic);
        }
        else if(i == 1) {
            Picasso.get().load(R.drawable.avatar2).fit().centerInside().into(profile_pic);
        }
        else if(i == 2) {
            Picasso.get().load(R.drawable.avatar3).fit().centerInside().into(profile_pic);
        }
        else if(i == 3) {
            Picasso.get().load(R.drawable.avatar4).fit().centerInside().into(profile_pic);
        }
        else if(i == 4) {
            Picasso.get().load(R.drawable.avatar5).fit().centerInside().into(profile_pic);
        }
        else if(i == 5) {
            Picasso.get().load(R.drawable.avatar6).fit().centerInside().into(profile_pic);
        }
        else if(i == 6) {
            Picasso.get().load(R.drawable.avatar7).fit().centerInside().into(profile_pic);
        }
        else if(i == 7) {
            Picasso.get().load(R.drawable.avatar8).fit().centerInside().into(profile_pic);
        }
        else if(i == 8) {
            Picasso.get().load(R.drawable.avatar9).fit().centerInside().into(profile_pic);
        }
        else if(i == 9) {
            Picasso.get().load(R.drawable.avatar10).fit().centerInside().into(profile_pic);
        }
        else if(i == 10) {
            Picasso.get().load(R.drawable.avatar11).fit().centerInside().into(profile_pic);
        }
        else if(i == 11) {
            Picasso.get().load(R.drawable.avatar12).fit().centerInside().into(profile_pic);
        }
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

    private void default_setup() {
        info1.setText(String.valueOf(user.getPostcount()));
        info1Text.setText("Posts");
        info2.setText(String.valueOf(user.getContribution()));
        info2Text.setText("Contribution");
        loadImage(user.getAvatarid());
        username.setText(user.firstName+" "+user.lastName);
        rating.setText("");
    }
}