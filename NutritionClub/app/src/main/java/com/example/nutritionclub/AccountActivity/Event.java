package com.example.nutritionclub.AccountActivity;

/**
 * Created by AhhBee on 1/8/2018.
 */

public class Event {

    String date;
    String venue;
    String time;
    String eventName;
    String details;

    public Event(){}

    public Event(String date, String venue, String time, String eventName, String details) {
        this.date = date;
        this.venue = venue;
        this.time = time;
        this.eventName = eventName;
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
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
