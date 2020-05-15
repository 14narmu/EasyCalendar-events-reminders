package com.example.easycalendar;

public class Time {
    private Integer hour;
    private Integer minute;
    private char sep = ':';

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }


    public Time(int hour, int minute, char sep) {
        this.hour = hour;
        this.minute = minute;
        this.sep = sep;
    }

    public Time(String hourAndMinute, char sep){
        String[] tokens = hourAndMinute.split(String.valueOf(sep));
       // if(tokens.length != 2)
            //throw Exception("Argument not proper for Time class");
        this.hour = Integer.valueOf(tokens[0]);
        this.minute = Integer.valueOf(tokens[1]);
        this.sep = Character.valueOf(sep);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public char getSep() {
        return sep;
    }

    public void setSep(char sep) {
        this.sep = sep;
    }

    public boolean isAfter(Time aTime){
        if(hour > aTime.getHour())
            return true;
        if(hour < aTime.getHour())
            return false;
        if(minute > aTime.getMinute())
            return true;
        else
            return false;
    }

    public boolean isEqual(Time aTime){
        if(hour == aTime.getHour() && minute == aTime.getMinute())
            return true;
        return false;
    }
    public boolean isBefore(Time aTime){
        if(isEqual(aTime))
            return false;
        return ! this.isAfter(aTime);
    }
    @Override
    public String toString(){
        return hour.toString() + sep + minute.toString();
    }
}
