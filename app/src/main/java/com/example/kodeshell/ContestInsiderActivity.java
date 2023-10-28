package com.example.kodeshell;


import static android.text.format.DateFormat.is24HourFormat;

import android.content.Context;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class ContestInsiderActivity extends AppCompatActivity {

    ImageView contestIcon,go_to_site;
    TextView contestName, contestDate, contestTime, contestDuration,startin;
    String URL;
    LinearLayout go_to_web_site,contest_add_to_cal,add_notification;
    private static Handler handler;
    private SimpleDateFormat dateFormat;
    private Date targetDate,startDate,endDate;
    Context context;

    MaterialTimePicker picker;

    ConstraintLayout notificationbg;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_insider);

        contestIcon = findViewById(R.id.contest_insider_site_icon);
        contestName = findViewById(R.id.contest_insider_contest_name);
        contestDate = findViewById(R.id.contest_insider_date);
        contestTime = findViewById(R.id.contest_insider_time);
        contestDuration = findViewById(R.id.contest_insider_duration);
        go_to_web_site=findViewById(R.id.contest_go_to_web_layout);
        go_to_site=findViewById(R.id.go_to_site);
        contest_add_to_cal=findViewById(R.id.contest_add_to_calender_layout);
        add_notification=findViewById(R.id.contest_add_notification_layout);
        startin=findViewById(R.id.contest_insider_time_countdown);
        notificationbg = findViewById(R.id.contest_insider_notification_button_bg);

        Picasso.get().load(getIntent().getIntExtra("image", 0)).fit().centerInside().into(contestIcon);
        // contestIcon.setImageResource(getIntent().getIntExtra("image", 0));

        contestName.setText(getIntent().getStringExtra("name"));
        contestDate.setText(getIntent().getStringExtra("date"));
        contestTime.setText(getIntent().getStringExtra("time"));
        contestDuration.setText(getIntent().getStringExtra("duration"));
        URL=getIntent().getStringExtra("URL");
        String starttime=getIntent().getStringExtra("st");
        String endtime=getIntent().getStringExtra("end");
        long start=convertAndAddHours(starttime,6);
        long end=convertAndAddHours(endtime,6);
        go_to_web_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast toast = Toast.makeText(getApplicationContext(), URL, Toast.LENGTH_SHORT);
               // toast.show();
                Intent intent = new Intent(ContestInsiderActivity.this, WebviewActivity.class);
                intent.putExtra("URL",URL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        handler = new Handler(Looper.getMainLooper());


        try {
            targetDate = dateFormat.parse(starttime);
            // Add 6 hours to the target date

            targetDate.setTime(targetDate.getTime() + 6 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }

        startCountdown();
        contest_add_to_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startDate = dateFormat.parse((starttime));

                    endDate = dateFormat.parse(endtime);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.TITLE, getIntent().getStringExtra("name"))
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, getIntent().getStringExtra("site"))
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDate.getTime()+6*3600*1000).putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endDate.getTime()+6*3600*1000)
                        .putExtra(CalendarContract.Events.DESCRIPTION, getIntent().getStringExtra("URL"));

                    startActivity(intent);


            }
        });

        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                .build();

        if(!isNotificationAdded()) {
            notificationbg.setBackgroundResource(R.drawable.custom_contest_circle_background);
        }
        else {
            notificationbg.setBackgroundResource(R.drawable.custom_added_notification_background);
        }


        add_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                    Toast toast = Toast.makeText(getApplicationContext(), URL, Toast.LENGTH_SHORT);
//                     toast.show();
//                    Intent intent = new Intent(ContestInsiderActivity.this, ContestReminder.class);
//                    intent.putExtra("name",getIntent().getStringExtra("name"));
//                    intent.putExtra("starttime",starttime);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);


                picker.show(getSupportFragmentManager(), "TAG");

                picker.addOnPositiveButtonClickListener(view1 -> contestReminder());

            }
        });




    }

    private boolean isNotificationAdded() {
        // will fetch data from database if notification has been added earlier or not
        return false;
    }

    private void contestReminder() {
        notificationbg.setBackgroundResource(R.drawable.custom_added_notification_background);

        // handle what will happen after clicking the add notification button
    }

    public static long convertAndAddHours(String iso8601DateString, int hoursToAdd) throws ParseException {
        Instant instant = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            instant = Instant.parse(iso8601DateString);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            instant = instant.plusSeconds(hoursToAdd * 3600); // 3600 seconds in an hour
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return instant.getEpochSecond();
        }

        return  0;
    }


    private void startCountdown() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                long targetTimeMillis = targetDate.getTime();
                long remainingMillis = targetTimeMillis - currentTimeMillis;

                if (remainingMillis <= 0) {
                    startin.setText("Running");
                } else {
                    long seconds = remainingMillis / 1000;
                    long hours = seconds / 3600;
                    long minutes = (seconds % 3600) / 60;
                    seconds = seconds % 60;

                    String countdownText = String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
                    startin.setText(countdownText);

                    handler.postDelayed(this, 1000); // Repeat every 1 second
                }
            }
        }, 0);
    }

}