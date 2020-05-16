package com.example.easycalendar;

import android.location.Location;



import org.threeten.bp.LocalDate;



public class MyEvent {
    private String eventName;
    private int index_category;
    private int color =-6543440;
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
    //TODO Delete after debug
    public MyEvent(Time time,int index_category,String eventName, LocalDate startDate) {
        this.index_category = index_category;
        this.eventName = eventName;
        this.startDate = startDate;
        this.startTime = time;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getIndex_category() {
        return index_category;
    }

    public void setIndex_category(int index_category) {
        this.index_category = index_category;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getIndex_notification() {
        return index_notification;
    }

    public void setIndex_notification(int index_notification) {
        this.index_notification = index_notification;
    }

    public int getEmail_notification() {
        return email_notification;
    }

    public void setEmail_notification(int email_notification) {
        this.email_notification = email_notification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRememberEmail() {
        return rememberEmail;
    }

    public void setRememberEmail(boolean rememberEmail) {
        this.rememberEmail = rememberEmail;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getIndex_recurrance() {
        return index_recurrance;
    }

    public void setIndex_recurrance(int index_recurrance) {
        this.index_recurrance = index_recurrance;
    }

    public Location getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(Location eventLocation) {
        this.eventLocation = eventLocation;
    }



}
