package com.example.ripetizioni_android.controller.adapter;

public class RecyclerViewSingleRow {
    private String title;
    private String body;
    private int idPren = -1;

    public RecyclerViewSingleRow(String title, String body){
        this.title = title;
        this.body = body;
    }

    public RecyclerViewSingleRow(int idPren, String title, String body){
        this.title = title;
        this.body = body;
        this.idPren = idPren;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getIdPren() {
        return idPren;
    }



    @Override
    public boolean equals(Object o) {
        if(o.getClass() == this.getClass()){
            return ((RecyclerViewSingleRow) o).getIdPren() == idPren ||
                    ((RecyclerViewSingleRow) o).getTitle().equals(this.title) && ((RecyclerViewSingleRow) o).getBody().equals(this.body);
        }
        return false;
    }

}
