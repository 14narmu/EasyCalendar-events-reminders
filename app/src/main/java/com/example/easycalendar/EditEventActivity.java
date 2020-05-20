package com.example.easycalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.realm.Realm;
import io.realm.RealmResults;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EditEventActivity extends AppCompatActivity implements View.OnClickListener{
    private Realm realm;
    private Button btn_editEvent;
    private Button btn_back;
    EventInformationFragment fragment;
    private int eventPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        //TODO
        Intent intent = getIntent();
        eventPos = intent.getIntExtra("position",0);

        initViews();
        setFragment();




    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_editEvent:
                updateEventFromDb(eventPos);
                break;
            case R.id.EditEventActivity_btn_back:
                //TODO EDIT
                RealmResults<MyEvent> allEvents = realm.where(MyEvent.class).findAll();
                MyEvent myEvent = allEvents.get(eventPos);
                fragment.setInputs(myEvent);
                break;
            default:
                break;
        }
    }

    void updateEventFromDb(int position){
        RealmResults<MyEvent> allEvents = realm.where(MyEvent.class).findAll();
        String primaryKey = allEvents.get(position).getpKey();
        MyEvent myEvent = fragment.getInputs();
        myEvent.setpKey(primaryKey);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(myEvent);
            }
        });


        Toast.makeText(this, "Etkinlik Güncellendi", Toast.LENGTH_SHORT).show();

        //close the activity and return the menu
        Intent intent = new Intent(EditEventActivity.this,MainActivity.class);
        finish();
        startActivity(intent);
    }

    private void setFragment() {

        //getting object
        RealmResults<MyEvent> allEvents = realm.where(MyEvent.class).findAll();
        MyEvent myEvent = allEvents.get(eventPos);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new EventInformationFragment();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
        fragmentTransaction.add(R.id.fragment_container, fragment);

        fragmentTransaction.runOnCommit( new Runnable(){
            @Override
            public void run() {
                fragment.setInputs(myEvent);
            }
        });
        fragmentTransaction.commit();







    }

    private void initViews() {

        btn_editEvent = findViewById(R.id.btn_editEvent);
        btn_back = findViewById(R.id.EditEventActivity_btn_back);
        realm = Realm.getDefaultInstance();

        btn_editEvent.setOnClickListener(this);
        btn_back.setOnClickListener(this);

    }
}
