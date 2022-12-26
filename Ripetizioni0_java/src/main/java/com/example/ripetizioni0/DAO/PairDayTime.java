package com.example.ripetizioni0.DAO;

public class PairDayTime {
    public String day;
    public int time;

    public PairDayTime(String day, int time) {
        this.day = day;
        this.time = time;
    }

    @Override
    public String toString() {
        return day + " " + time;
    }
}
