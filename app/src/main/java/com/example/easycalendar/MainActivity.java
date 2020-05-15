package com.example.easycalendar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MaterialCalendarView myCalendar;
    private Button addEvent;
    private CalendarDay selectedDate;
    private ImageButton btn_upcomingEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCalendar = findViewById(R.id.myCalendar);
        addEvent = findViewById(R.id.addEvent);
        btn_upcomingEvents = findViewById(R.id.btn_upcomingEvents);

        selectedDate = CalendarDay.today();
        myCalendar.setDateSelected(CalendarDay.today(),true);


        myCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedDate = date;
            }
        });

        btn_upcomingEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UpComingEventsList.class);
                startActivity(intent);
            }
        });
       CalendarDay mydate=CalendarDay.from(2020,  5, 21); // year, month, date
        HashSet<CalendarDay> dates = new HashSet<>();
        dates.add(mydate);
        myCalendar.addDecorators(new EventDecorator(0, dates,getApplicationContext()));

        CalendarDay mydate2=CalendarDay.from(2020,  5, 20); // year, month, date
        dates.add(mydate2);
        myCalendar.addDecorators(new EventDecorator(getColor(R.color.colorAccent), dates,getApplicationContext()));
/*
        myCalendar.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                selectedDate = eventDay.getCalendar();
                calendars.add(selectedDate);
                myCalendar.setHighlightedDays(calendars);
                //previewNote(eventDay);



            }
        });*/

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddEventActivity.class);
                String date = selectedDate.getDay()+"/"+selectedDate.getMonth()+"/"+selectedDate.getYear();
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });


    }
}
