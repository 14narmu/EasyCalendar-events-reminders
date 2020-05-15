package com.example.easycalendar;

public class EventDay {
    private String eventName;
    private Integer color;
    //start time
    //
    public EventDay(String eventName){
        this.eventName  = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
