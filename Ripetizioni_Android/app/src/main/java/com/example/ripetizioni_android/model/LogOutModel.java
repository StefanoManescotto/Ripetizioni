package com.example.ripetizioni_android.model;

import android.os.AsyncTask;

import com.example.ripetizioni_android.controller.MainActivity;
import com.example.ripetizioni_android.controller.fragments.AccountFragment;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class LogOutModel extends AsyncTask<Void, Void, Void> {
    private AccountFragment accountFragment;

    public LogOutModel(AccountFragment accountFragment){
        this.accountFragment = accountFragment;
    }

    private void logOut(){
        HttpUrl url = HttpUrl.parse("http://192.168.1.28:8080/Ripetizioni0_java_war_exploded/logOut").newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(url.url())
                .build();
        try {
            MainActivity.client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        logOut();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        accountFragment.invalidateAccountData();
    }
}
