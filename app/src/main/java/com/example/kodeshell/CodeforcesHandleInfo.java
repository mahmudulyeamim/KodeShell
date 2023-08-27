package com.example.kodeshell;


import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
public class CodeforcesHandleInfo extends AppCompatActivity {

    private CodeforcesAPIHelper apiHelper;
    private TextView handleTextView;
    private TextView ratingTextView;
    private EditText codeforcesusername;
    private Button fatchbutton;
    private Button last_submisson;
    ImageView avatarImageView ;
    String codeforceshandlename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeforces_handle_info);

        apiHelper = new CodeforcesAPIHelper(this);
        handleTextView = findViewById(R.id.handleTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        codeforcesusername=findViewById(R.id.handleEditText);
        fatchbutton=findViewById(R.id.fetchButton);
        last_submisson=findViewById(R.id.last);
        avatarImageView = findViewById(R.id.avatarImageView);

        last_submisson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiHelper.getUserLastSubmissions(codeforceshandlename, 10 , new CodeforcesAPIHelper.UserInfoListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            StringBuilder problemNames = new StringBuilder();
                            JSONArray submissions = response.getJSONArray("result");
                            for (int i = 0; i < submissions.length(); i++) {
                                JSONObject submission = submissions.getJSONObject(i);
                                JSONObject problem = submission.getJSONObject("problem");
                                String problemName = problem.getString("name") ;

                                String tags= problem.getString("tags");

                                String verdict1= submission.getString("verdict");
                                problemNames.append(problemName);
                                problemNames.append(" ");
                                problemNames.append(verdict1);

                                problemNames.append("\n");
                            }

                            TextView problemNamesTextView = findViewById(R.id.problemNamesTextView);
                            problemNamesTextView.setText(problemNames.toString());
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
        });

        fatchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeforceshandlename = codeforcesusername.getText().toString();


                apiHelper.getUserInfo(codeforceshandlename, new CodeforcesAPIHelper.UserInfoListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            JSONObject user = response.getJSONArray("result").getJSONObject(0);
                            String handle = user.getString("handle");
                            int rating = user.getInt("rating");

                            String imageUrl = user.getString("titlePhoto");



                            runOnUiThread(() -> {
                                handleTextView.setText("Handle: " + handle);
                                ratingTextView.setText("Rating: " + rating);
                                Picasso.get().load(imageUrl).into(avatarImageView);
                            });

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
        });


    }
}