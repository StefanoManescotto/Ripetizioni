package com.example.ripetizioni_android.model;

import android.os.AsyncTask;

import com.example.ripetizioni_android.controller.MainActivity;
import com.example.ripetizioni_android.controller.fragments.PrenFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class PrenotazioniModel extends AsyncTask<Void, Void, Void> {
    private String ris;
    private final PrenFragment prenFragment;

    public PrenotazioniModel(PrenFragment prenFragment){
        this.prenFragment = prenFragment;
    }

    private void connect(){
        Request request = new Request.Builder()
                .url("http://192.168.1.28:8080/Ripetizioni0_java_war_exploded/prenotazioniUtente")
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
    protected Void doInBackground(Void... voids) {
        connect();

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(ris != null) {
            try {
                JSONArray json = new JSONArray(ris);
                prenFragment.updateAdapter(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
