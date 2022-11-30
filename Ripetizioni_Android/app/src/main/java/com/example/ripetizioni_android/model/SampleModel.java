package com.example.ripetizioni_android.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SampleModel {
    private void connect(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("KEY1", "VALUE1");
            jsonObject.put("KEY2", "VALUE2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url("http://192.168.1.30:8080/Ripetizioni0_java_war_exploded/ripetizioni")
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            String resStr = response.body().string();
            //jsonObject = new JSONObject(response.body().string());
            System.out.println("\n\nResponse: " + resStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
