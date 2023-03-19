package com.example.ripetizioni_android.model;

import android.os.AsyncTask;

import com.example.ripetizioni_android.controller.MainActivity;
import com.example.ripetizioni_android.controller.fragments.HomeFragment;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class PrenotaModel extends AsyncTask<String, Void, Void> {
    private String ris;
    private HomeFragment homeFragment;

    public PrenotaModel(HomeFragment homeFragment){
        this.homeFragment = homeFragment;
    }

    private void prenota(String[] params){
        HttpUrl url = HttpUrl.parse("http://192.168.1.28:8080/Ripetizioni0_java_war_exploded/prenota").newBuilder()
                .addQueryParameter("corso", params[0])
                .addQueryParameter("nome", params[1])
                .addQueryParameter("cognome", params[2])
                .addQueryParameter("data", params[3])
                .addQueryParameter("ora", params[4])
                .build();
        System.out.println(url.url());

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
    }

    @Override
    protected Void doInBackground(String... params) {
        prenota(params);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        homeFragment.printServerResponse(ris);
    }
}
