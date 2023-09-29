package com.example.kodeshell;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LeetcodeAPIHelper {
    private static final String BASE_URL = "https://leetcode.com/graphql";
    private OkHttpClient client;
    private Handler mainHandler;

    public LeetcodeAPIHelper() {
        client = new OkHttpClient();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public interface ContestListCallback {
        void onSuccess(JSONObject userObject);
        void onFailure(Exception e);
    }

    public void executeQuery(String query, JSONObject variables, final ContestListCallback callback) {
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("query", query);
            requestBody.put("variables", variables);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(mediaType, requestBody.toString());

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String jsonResponse = response.body().string();
                        JSONObject userObject = new JSONObject(jsonResponse);
                        mainHandler.post(() -> callback.onSuccess(userObject));
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
