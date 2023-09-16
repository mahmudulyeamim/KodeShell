package com.example.kodeshell;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CodeforcesAPIHelper {

    private static final String API_BASE_URL = "https://codeforces.com/api/";
    private RequestQueue requestQueue;

    public CodeforcesAPIHelper(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public interface UserInfoListener {
        void onSuccess(JSONObject response);
        void onError(String errorMessage);
    }

    public void getUserInfo(String handle, UserInfoListener listener) {
        String url = API_BASE_URL +"user.info?handles="+ handle;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> listener.onSuccess(response),
                error -> listener.onError("Error fetching user info: " + error.getMessage())
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void getUserLastSubmissions(String handle, int count, UserInfoListener listener) {
        String url = API_BASE_URL + "user.status?handle=" + handle + "&count=" + count;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> listener.onSuccess(response),
                error -> listener.onError("Error fetching user submissions: " + error.getMessage())
        );
        requestQueue.add(jsonObjectRequest);
    }

    public void fetchUpcomingContests(CodeforcesAPIHelper.UserInfoListener listener) {
        String url = API_BASE_URL + "contest.list?gym=false";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> listener.onSuccess(response),
                error -> listener.onError("Error fetching upcoming contests: " + error.getMessage())
        );

        requestQueue.add(jsonObjectRequest);
    }
}
