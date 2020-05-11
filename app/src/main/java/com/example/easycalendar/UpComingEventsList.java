package com.example.easycalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class UpComingEventsList extends AppCompatActivity {
private RecyclerView recyclerView_UpcomingEvents;
    LinearLayoutManager layoutManager;
    ArrayList<MyEvent> events;
    EventAdapter eventAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_events_list);

        recyclerView_UpcomingEvents = findViewById(R.id.recyclerView_UpcomingEvents);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_UpcomingEvents.setLayoutManager(layoutManager);
        events = new ArrayList<>();
        events.add(new MyEvent("Deneme1"));
        events.add(new MyEvent("Deneme2"));
        events.add(new MyEvent("Deneme3"));
        events.add(new MyEvent("Deneme4"));
        events.add(new MyEvent("Deneme5"));
        events.add(new MyEvent("Deneme1"));
        events.add(new MyEvent("Deneme2"));
        events.add(new MyEvent("Deneme3"));
        events.add(new MyEvent("Deneme4"));
        events.add(new MyEvent("Deneme5"));
        events.add(new MyEvent("Deneme1"));
        events.add(new MyEvent("Deneme2"));
        events.add(new MyEvent("Deneme3"));
        events.add(new MyEvent("Deneme4"));
        events.add(new MyEvent("Deneme5"));
        eventAdapter = new EventAdapter(this,events);
        recyclerView_UpcomingEvents.setAdapter(eventAdapter);
    }
}
