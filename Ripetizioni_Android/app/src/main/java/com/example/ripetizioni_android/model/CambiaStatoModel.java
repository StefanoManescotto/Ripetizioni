package com.example.ripetizioni_android.model;

import android.os.AsyncTask;

import com.example.ripetizioni_android.controller.MainActivity;
import com.example.ripetizioni_android.controller.fragments.PrenFragment;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class CambiaStatoModel extends AsyncTask<String, Void, Void> {
    private String ris, state;
    private PrenFragment prenFragment;
    private int idPren;

    public CambiaStatoModel(PrenFragment prenFragment){
        this.prenFragment = prenFragment;
    }

    private void cambiaStato(String[] params){
        HttpUrl url = HttpUrl.parse("http://192.168.1.28:8080/Ripetizioni0_java_war_exploded/cambiaStato").newBuilder()
                .addQueryParameter("idPrenotazione", params[0])
                .addQueryParameter("newState", params[1])
                .build();

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
        idPren = Integer.parseInt(params[0]);
        state = params[1];
        cambiaStato(params);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        prenFragment.reload();
    }
}
