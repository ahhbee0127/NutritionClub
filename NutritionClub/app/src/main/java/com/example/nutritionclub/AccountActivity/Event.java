package com.example.nutritionclub.AccountActivity;

/**
 * Created by AhhBee on 1/8/2018.
 */

public class Event {

    String date;
    String timeTo;
    String timeFrom;
    String eventName;
    String details;
    String eventId;

    public Event(){}

    public Event(String eventId,String date, String timeTo, String timeFrom, String eventName, String details) {
        this.eventId = eventId;
        this.date = date;
        this.timeTo = timeTo;
        this.timeFrom = timeFrom;
        this.eventName = eventName;
        this.details = details;
    }

    public String getEventId() {
        return eventId;
    }

    public String getDate() {
        return date;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDetails() {
        return details;
    }
}
