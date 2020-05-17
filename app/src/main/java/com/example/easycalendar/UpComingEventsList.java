package com.example.easycalendar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UpComingEventsList extends AppCompatActivity implements View.OnClickListener{
private RecyclerView recyclerView_UpcomingEvents;
    private LinearLayoutManager layoutManager;
    private ArrayList<MyEvent> events;
    private EventAdapter eventAdapter;
    private ImageButton btn_mainMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_events_list);

        recyclerView_UpcomingEvents = findViewById(R.id.recyclerView_UpcomingEvents);
        btn_mainMenu = findViewById(R.id.btn_MainMenu);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_UpcomingEvents.setLayoutManager(layoutManager);

        events = new ArrayList<>();
        fillEvents(events); //dynamically fills view for debugging

        Collections.sort(events, new Comparator<MyEvent>() {
            @Override
            public int compare(MyEvent o1, MyEvent o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });

        setRecyclerView_UpcomingEvents();

        setRecyclerView_UpcomingEventsListener();
        btn_mainMenu.setOnClickListener(this);


    }

    public static void fillEvents (ArrayList<MyEvent> events){
        //debugging purpose
        Time t = new Time(12,50);
        events.add(new MyEvent(t,1,"Deneme1",  LocalDate.of(2020,10,10)));
        events.add(new MyEvent(t,2,"Deneme2", LocalDate.of(2020,10,10)));
        events.add(new MyEvent(t,3,"Deneme3",LocalDate.of(2020,5,4)));
        events.add(new MyEvent(t,4,"Deneme4", LocalDate.of(2020,5,5)));
        events.add(new MyEvent(t,2,"Deneme5", LocalDate.of(2020,6,15)));
        events.add(new MyEvent(t,2,"Deneme6", LocalDate.of(2020,1,30)));
        events.add(new MyEvent(t,1,"Deneme7", LocalDate.of(2020,5,3)));
    }

    private void setRecyclerView_UpcomingEventsListener() {
        recyclerView_UpcomingEvents.addOnItemTouchListener(
                new RecyclerViewItemClickListener(UpComingEventsList.this, recyclerView_UpcomingEvents ,new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Toast.makeText(UpComingEventsList.this, position + " a basıldı", Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Toast.makeText(UpComingEventsList.this, position + " a uzun basıldı", Toast.LENGTH_SHORT).show();
                        showAlertDialog(position);
                    }
                })
        );
    }
    private void showAlertDialog(int position){
        AlertDialog.Builder options  = new AlertDialog.Builder(UpComingEventsList.this);
        options.setMessage("Etkinlik ayarları");
        options.setNeutralButton("Düzenle",null);
        options.setNegativeButton("Sil", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteEvent(position);
            }
        });
        options.setPositiveButton("Tamam", null);
        options.show();
    }

    private void setRecyclerView_UpcomingEvents(){
        eventAdapter = new EventAdapter(this,events);
        recyclerView_UpcomingEvents.setAdapter(eventAdapter);
    }

    private void deleteEvent(int position) {
        events.remove(position);
        Toast.makeText(this, "Etkinlik Silindi", Toast.LENGTH_SHORT).show();
        setRecyclerView_UpcomingEvents();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_MainMenu:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
