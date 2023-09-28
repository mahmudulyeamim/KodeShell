package com.example.kodeshell;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AtcoderAPIHelper {

    private static final String BASE_URL = "https://atcoder.jp/api/v3";

    private OkHttpClient client;
    private Handler mainHandler;

    public AtcoderAPIHelper() {
        client = new OkHttpClient();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public interface ContestListCallback {
        void onSuccess(JSONArray contestArray);
        void onFailure(Exception e);
    }

    public void getUpcomingContests(final ContestListCallback callback) {
        String url = "https://kontests.net/api/v1/all";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray contestArray = new JSONArray(response.body().string());
                        mainHandler.post(() -> callback.onSuccess(contestArray));
                    } catch (JSONException e) {
                        onFailure(call, new IOException("JSON parsing error", e));
                    }
                } else {
                    onFailure(call, new IOException("Request failed with code " + response.code()));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() -> callback.onFailure(e));
            }
        });
    }



    public void getAtcoderContestHistoryOfUser(final ContestListCallback callback,String username) {
        String url = "https://atcoder.jp/users/"+username+"/history/json";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray usercontesthistoryArray = new JSONArray(response.body().string());
                        mainHandler.post(() -> callback.onSuccess(usercontesthistoryArray));
                    } catch (JSONException e) {
                        onFailure(call, new IOException("JSON parsing error", e));
                    }
                } else {
                    onFailure(call, new IOException("Request failed with code " + response.code()));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() -> callback.onFailure(e));
            }
        });
    }


    public void getAtcodersubmissionHistoryOfUser(final ContestListCallback callback,String username) {
        String url = "https://kenkoooo.com/atcoder/atcoder-api/v3/user/submissions?user="+username+"&from_second=100";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray usersubmissionhistoryArray = new JSONArray(response.body().string());
                        mainHandler.post(() -> callback.onSuccess(usersubmissionhistoryArray));
                    } catch (JSONException e) {
                        onFailure(call, new IOException("JSON parsing error", e));
                    }
                } else {
                    onFailure(call, new IOException("Request failed with code " + response.code()));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() -> callback.onFailure(e));
            }
        });
    }


}
