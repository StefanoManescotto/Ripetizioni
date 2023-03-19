package com.example.ripetizioni_android.model;

import android.os.AsyncTask;

import com.example.ripetizioni_android.controller.MainActivity;
import com.example.ripetizioni_android.controller.fragments.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DocentiModel extends AsyncTask<String, Void, Void> {
    String ris;
    HomeFragment homeFragment;

    public DocentiModel(HomeFragment homeFragment){
        this.homeFragment = homeFragment;
    }

    @Override
    protected Void doInBackground(String... strings) {
        Calendar nextWeek = homeFragment.getNextWeek();
        String from = new SimpleDateFormat("yyyy-MM-dd").format(nextWeek.getTime());
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://192.168.1.28:8080/Ripetizioni0_java_war_exploded/docenti").newBuilder()
                .addQueryParameter("from", from);

        nextWeek.add(Calendar.DAY_OF_WEEK, 4);
        String to = new SimpleDateFormat("yyyy-MM-dd").format(nextWeek.getTime());
        HttpUrl url = urlBuilder.addQueryParameter("to", to)
                .addQueryParameter("materia", strings[0]).build();

        Request request = new Request.Builder()
                .url(url.url())
                .build();

        Response response;
        try {
            response = MainActivity.client.newCall(request).execute();
            ris = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(ris != null) {
            try {
                JSONArray json = new JSONArray(ris);
                homeFragment.updateDialogAdapter(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
