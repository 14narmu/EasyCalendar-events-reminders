package com.example.easycalendar;

import android.location.Location;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.threeten.bp.LocalDate;

import java.util.Date;

public class MyEvent {
    private String eventName;
    private int index_category;
    private int color;
    private Time startTime;
    private Time endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private int index_notification;
    private int email_notification;
    private String email;
    private boolean rememberEmail;
    private String notes;
    private int index_recurrance;
    private Location eventLocation;

    public MyEvent(String eventName, int index_category, int color, Time startTime, Time endTime,
                   LocalDate startDate, LocalDate endDate, int index_notification, String notes, int index_recurrance) {
        this.eventName = eventName;
        this.index_category = index_category;
        this.color = color;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.index_notification = index_notification;
        this.notes = notes;
        this.index_recurrance = index_recurrance;
    }
    public static LocalDate StringToDate(String dateString,char sep){
        String [] tokens = dateString.split(String.valueOf(sep));
        //if(tokens.length != 3)

        LocalDate date = LocalDate.of( Integer.valueOf(tokens[2]), Integer.valueOf(tokens[1]),Integer.valueOf(tokens[0]) );
        return date;
    }

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
