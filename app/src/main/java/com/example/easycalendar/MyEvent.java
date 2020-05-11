package com.example.easycalendar;

public class MyEvent {
    private String eventName;
    public MyEvent(String eventName){
        this.eventName  = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
