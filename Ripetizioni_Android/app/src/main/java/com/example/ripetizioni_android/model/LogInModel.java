package com.example.ripetizioni_android.model;

import android.os.AsyncTask;

import com.example.ripetizioni_android.controller.MainActivity;
import com.example.ripetizioni_android.controller.fragments.LogInFragment;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogInModel extends AsyncTask<String, Void, Void> {
    private String ris  , cookie;
    private LogInFragment logInFragment;

    public LogInModel(LogInFragment logInFragment){
        this.logInFragment = logInFragment;
    }

    private void logIn(String[] params){
        HttpUrl url = HttpUrl.parse("http://192.168.1.28:8080/Ripetizioni0_java_war_exploded/logIn").newBuilder()
                .build();

        RequestBody body = new FormBody.Builder()
                .add("email", params[0])
                .add("password", params[1])
                .build();
        Request request = new Request.Builder()
                .url(url.url())
                .post(body)
                .build();

        Response response;
        try {
            response = MainActivity.client.newCall(request).execute();
            cookie = response.headers().get("Set-Cookie");
            ris = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        logIn(params);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(ris != null && ris.equals("200")){
            logInFragment.changeAccountFragment(cookie);
        }else if(ris == null){
            logInFragment.printStatus("LogIn Fallito: problema nel contattare il server");
        }else{
            logInFragment.printStatus("LogIn Fallito: controlla i dati inseriti");
        }
        logInFragment.enableAll();
    }
}
