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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MaterialCalendarView myCalendar;
    private Button addEvent;
    private CalendarDay selectedDate;
    private ImageButton btn_upcomingEvents;
    private ListView dailyEventList;

    private ArrayList<MyEvent> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();




        myCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedDate = date;
                showDailyEvents();
            }
        });

        btn_upcomingEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UpComingEventsList.class);
                startActivity(intent);
            }
        });
        calendarDecorate();

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

    private void calendarDecorate() {
        HashSet<CalendarDay> eventDays = new HashSet<>();
       for (MyEvent aEvent : events){
           eventDays.add(CalendarDay.from(aEvent.getStartDate()));
       }

        myCalendar.addDecorators(new EventDecorator(getColor(R.color.colorAccent), eventDays,getApplicationContext()));
    }

    private void initViews() {
        events = new ArrayList<>();
        UpComingEventsList.fillEvents(events);

        myCalendar = findViewById(R.id.myCalendar);
        addEvent = findViewById(R.id.addEvent);
        btn_upcomingEvents = findViewById(R.id.btn_upcomingEvents);
        dailyEventList = findViewById(R.id.dailyEventList);

        selectedDate = CalendarDay.today();
        myCalendar.setDateSelected(CalendarDay.today(),true);
        decorateToday();
    }
    private void decorateToday(){
        HashSet<CalendarDay> eventDays = new HashSet<>();
        eventDays.add(CalendarDay.today());
        myCalendar.addDecorators(new EventDecorator(1, eventDays,getApplicationContext()));
    }
    private void showDailyEvents(){

        ArrayList<MyEvent> dayEvents = new ArrayList<>();
        for(MyEvent aEvent : events){
            if ( aEvent.getStartDate().equals(selectedDate.getDate()) )
                dayEvents.add(aEvent);
        }


        DailyEventAdapter adapter_daily =  new DailyEventAdapter(getApplicationContext(),dayEvents);
        dailyEventList.setAdapter(adapter_daily);
    }
}
