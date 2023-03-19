package com.example.ripetizioni_android.model;

import android.os.AsyncTask;

import com.example.ripetizioni_android.controller.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticateModel extends AsyncTask<String, Void, Void> {
    private String ris;
    private MainActivity mainActivity;

    public AuthenticateModel(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    private void authenticate(String cookie){
        HttpUrl url = HttpUrl.parse("http://192.168.1.28:8080/Ripetizioni0_java_war_exploded/authenticate").newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(url.url())
                .addHeader("cookie", cookie)
                .build();

        Response response;
        try {
            response = MainActivity.client.newCall(request).execute();
            ris = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        authenticate(params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(ris == null || ris.equals("401")){
            mainActivity.setEmptySharedPreferences();
        }else{
            try {
                JSONObject json = new JSONObject(ris);
                mainActivity.setSharedPreferences(json.getString("name"), json.getString("surname"), json.getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
