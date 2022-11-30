package com.example.ripetizioni_android.controller.adapter;

public class RecyclerViewSingleRow {
    private String title;
    private String body;

    public RecyclerViewSingleRow(String title, String body){
        this.title = title;
        this.body = body;
    }

    @Override
    public String toString() {
        return "ora = " + title + " body = " + body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
