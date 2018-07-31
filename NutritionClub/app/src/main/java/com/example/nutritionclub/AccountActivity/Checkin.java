package com.example.nutritionclub.AccountActivity;

/**
 * Created by AhhBee on 31/7/2018.
 */

public class Checkin {

    String date;
    String name;
    String time;

    public  Checkin(){}

    public Checkin(String date, String name, String time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
