package com.example.nutritionclub.AccountActivity;

/**
 * Created by AhhBee on 1/8/2018.
 */

public class Event {

    String date;
    String time;
    String eventName;
    String details;
    String eventId;

    public Event(){}

    public Event(String eventId,String date, String time, String eventName, String details) {
        this.eventId = eventId;
        this.date = date;
        this.time = time;
        this.eventName = eventName;
        this.details = details;
    }

    public String getEventId() {
        return eventId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDetails() {
        return details;
    }
}
