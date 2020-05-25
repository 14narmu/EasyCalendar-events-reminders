package com.example.easycalendar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import io.realm.Realm;
import io.realm.RealmResults;


import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MaterialCalendarView myCalendar;
    private TextView dayNum;
    private TextView monthAndYear;
    private ImageButton preferences;
    private Button addEvent;
    private CalendarDay selectedDate;
    private ImageButton btn_upcomingEvents;
    private ListView dailyEventList;
    private Realm realm;

    private ArrayList<MyEvent> allEvents;
    private HashMap<String,MyEvent> allEvents2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(android.R.style.);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initViews();
        setListeners();

        myCalendar.setSelectionColor(getColor(R.color.colorAccent));




    }

    private void setListeners() {


        myCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedDate = date;
                listDailyEvents();
            }
        });


        preferences.setOnClickListener(this);
        btn_upcomingEvents.setOnClickListener(this);
        addEvent.setOnClickListener(this);
    }

    private void circleDecorate(){
        int [] colors = getResources().getIntArray(R.array.demo_colors);
        HashSet<CalendarDay> eventDays;
        for(int currentColor : colors) {
           eventDays = new HashSet<>();
           for (MyEvent aEvent : allEvents) {
               if (aEvent.getColor() == currentColor) {
                   String[] tokens = aEvent.getStartDate().split("-");
                   int year = Integer.valueOf(tokens[0]);
                   int month = Integer.valueOf(tokens[1]);
                   int day = Integer.valueOf(tokens[2]);
                   eventDays.add(CalendarDay.from(year, month, day));
               }
           }
           myCalendar.addDecorators(new EventDecorator(currentColor, eventDays, getApplicationContext()));
        }
    }
 /*   private void circleDecorate(){
        int [] colors = getResources().getIntArray(R.array.demo_colors);
        HashSet<CalendarDay> eventDays;
        for(int currentColor : colors) {
            eventDays = new HashSet<>();
            for( String date : allEvents2.keySet()){

            }
            for (MyEvent aEvent : allEvents) {
                if (aEvent.getColor() == currentColor) {
                    String[] tokens = aEvent.getStartDate().split("-");
                    int year = Integer.valueOf(tokens[0]);
                    int month = Integer.valueOf(tokens[1]);
                    int day = Integer.valueOf(tokens[2]);
                    eventDays.add(CalendarDay.from(year, month, day));
                }
            }
            myCalendar.addDecorators(new EventDecorator(currentColor, eventDays, getApplicationContext()));
        }
    }*/
    private void initViews() {
        allEvents = new ArrayList<>();
        allEvents2 = new HashMap<>();
        myCalendar = findViewById(R.id.myCalendar);
        addEvent = findViewById(R.id.addEvent);
        btn_upcomingEvents = findViewById(R.id.btn_upcomingEvents);
        dailyEventList = findViewById(R.id.dailyEventList);
        monthAndYear = findViewById(R.id.MainActivity_monthNameAndYear);
        dayNum = findViewById(R.id.MainActivity_dayNumber);
        preferences = findViewById(R.id.MainActivity_btn_preferences);
        selectedDate = CalendarDay.today();
        myCalendar.setDateSelected(CalendarDay.today(),true);
        realm = Realm.getDefaultInstance();
        getEventsFromDb();
        decorateToday();
        circleDecorate();
        listDailyEvents();
    }

    private void getEventsFromDb() {

        RealmResults<MyEvent> eventsDb = realm.where(MyEvent.class).findAll();
        for(MyEvent aEvent : eventsDb){
            allEvents.add(aEvent);
            allEvents2.put(aEvent.getStartDate(),aEvent);
        }
    }

    private void decorateToday(){
        //allows us to distinguish today's appearance from other days
        HashSet<CalendarDay> eventDays = new HashSet<>();
        eventDays.add(CalendarDay.today());
        myCalendar.addDecorators(new EventDecorator(1, eventDays,getApplicationContext()));

        //add today's date to top bar
        String month = getResources().getStringArray
                (R.array.months)[CalendarDay.today().getMonth()-1];
        dayNum.setText(String.valueOf(CalendarDay.today().getDay()));
        monthAndYear.setText(month + " " +CalendarDay.today().getYear());

    }
    private void listDailyEvents(){

        ArrayList<MyEvent> dayEvents = new ArrayList<>();

        for(MyEvent aEvent : allEvents){

            if ( aEvent.getStartDate().equals(selectedDate.getDate().toString()) )
                dayEvents.add(aEvent);

        }

        DailyEventAdapter adapter_daily =  new DailyEventAdapter(getApplicationContext(),dayEvents);
        dailyEventList.setAdapter(adapter_daily);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.MainActivity_btn_preferences:
                intent = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_upcomingEvents:
                intent = new Intent(getApplicationContext(),UpComingEventsList.class);
                startActivity(intent);
                break;
            case R.id.addEvent:
                intent = new Intent(getApplicationContext(), AddEventActivity.class);
                String date = selectedDate.getDate().toString();
                intent.putExtra("date",date);
                startActivity(intent);
                break;
        }
    }
}
