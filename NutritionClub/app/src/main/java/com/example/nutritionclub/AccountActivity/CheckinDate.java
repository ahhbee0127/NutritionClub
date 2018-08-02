package com.example.nutritionclub.AccountActivity;

/**
 * Created by AhhBee on 3/8/2018.
 */

public class CheckinDate {
    String checkinDateId;
    String date;

    public CheckinDate(String checkinDateId, String date) {
        this.checkinDateId = checkinDateId;
        this.date = date;
    }

    public CheckinDate(String date){
        this.date = date;
    }

    public CheckinDate(){}


    public String getCheckinDateId() {
        return checkinDateId;
    }

    public String getDate() {
        return date;
    }
}
