package com.example.easycalendar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpComingEventsList extends AppCompatActivity implements View.OnClickListener{
private RecyclerView recyclerView_UpcomingEvents;
    private LinearLayoutManager layoutManager;
   // private ArrayList<MyEvent> allEvents;
   RealmResults<MyEvent> eventsDb;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
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

        eventsDb = realm.where(MyEvent.class).findAll();

        eventsDb = eventsDb.sort("startDate", Sort.ASCENDING,
        "startTime.hour",Sort.ASCENDING );

        setRecyclerView_UpcomingEvents();

        setRecyclerView_UpcomingEventsListener();
        btn_mainMenu.setOnClickListener(this);


    }


    private void setRecyclerView_UpcomingEventsListener() {
        recyclerView_UpcomingEvents.addOnItemTouchListener(
                new RecyclerViewItemClickListener(UpComingEventsList.this, recyclerView_UpcomingEvents ,new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String primaryKey = eventsDb.get(position).getpKey();
                        Toast.makeText(UpComingEventsList.this, position + " a basıldı", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpComingEventsList.this, EditEventActivity.class);
                        intent.putExtra("primaryKey",primaryKey);
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
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getApplicationContext(),eventsDb);
        recyclerView_UpcomingEvents.setAdapter(myRecyclerViewAdapter);
    }

    private void deleteEvent(int position) {

        //find parent primary key for recurrence events
        String [] pkey = eventsDb.get(position).getpKey().split("_");
        String parentPkey = pkey[0];

        RealmResults<MyEvent> results = realm.where(MyEvent.class)
                .contains("pKey", parentPkey)
                .findAll();

        realm.executeTransaction(realm -> {
            // Delete all matches
            results.deleteAllFromRealm();
        });
        Toast.makeText(this, results.size() + " Etkinlik Silindi", Toast.LENGTH_SHORT).show();
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
