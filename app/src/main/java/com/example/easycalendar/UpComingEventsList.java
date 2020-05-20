package com.example.easycalendar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UpComingEventsList extends AppCompatActivity implements View.OnClickListener{
private RecyclerView recyclerView_UpcomingEvents;
    private LinearLayoutManager layoutManager;
    private ArrayList<MyEvent> allEvents;
    private EventAdapter eventAdapter;
    private ImageButton btn_mainMenu;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_events_list);

        recyclerView_UpcomingEvents = findViewById(R.id.recyclerView_UpcomingEvents);
        btn_mainMenu = findViewById(R.id.btn_MainMenu);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_UpcomingEvents.setLayoutManager(layoutManager);
        realm = Realm.getDefaultInstance();

        allEvents = new ArrayList<>();
        //fillEvents(events); //dynamically fills view for debugging
        getEventsFromDb();

        Collections.sort(allEvents, new Comparator<MyEvent>() {
            @Override
            public int compare(MyEvent o1, MyEvent o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });

        setRecyclerView_UpcomingEvents();

        setRecyclerView_UpcomingEventsListener();
        btn_mainMenu.setOnClickListener(this);


    }


    private void setRecyclerView_UpcomingEventsListener() {
        recyclerView_UpcomingEvents.addOnItemTouchListener(
                new RecyclerViewItemClickListener(UpComingEventsList.this, recyclerView_UpcomingEvents ,new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Toast.makeText(UpComingEventsList.this, position + " a basıldı", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpComingEventsList.this, EditEventActivity.class);
                        intent.putExtra("position",position);
                        startActivity(intent);
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
        eventAdapter = new EventAdapter(this,allEvents);
        recyclerView_UpcomingEvents.setAdapter(eventAdapter);
    }

    private void deleteEvent(int position) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                allEvents.get(position).deleteFromRealm();
            }
        });
        allEvents.remove(position);
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
    private void getEventsFromDb() {

        RealmResults<MyEvent> eventsDb = realm.where(MyEvent.class).findAll();
        for(MyEvent aEvent : eventsDb){
            allEvents.add(aEvent);
            //Log.i("Realm kaydı : ","etkinlik adı" + aEvent.getEventName()+" tarihi" + aEvent.getStartDate());
        }
    }
}
