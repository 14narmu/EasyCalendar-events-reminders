package com.example.easycalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.realm.Realm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.time.LocalDate;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener{
    private Realm realm;
    private Button btn_addEvent;
    private Button btn_back;
    String chosenDate;
    EventInformationFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Intent intent = getIntent();
        chosenDate = intent.getStringExtra("date");

        initViews();

        createFragment();


    }

    private void createFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment = new EventInformationFragment().newInstance(chosenDate);
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void initViews() {
        btn_addEvent = findViewById(R.id.btn_completeAddingEvent);
        btn_back = findViewById(R.id.AddEventActivity_btn_back);
        realm = Realm.getDefaultInstance();

        btn_addEvent.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    private void addEventToDb(){

        MyEvent myEvent = fragment.getInputs();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyEvent event = realm.copyToRealm(myEvent);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(AddEventActivity.this, "Etkinlik kaydedildi", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(AddEventActivity.this, "Bir hata meydana geldi", Toast.LENGTH_SHORT).show();
            }
        });

        //close the activity and return the menu
        Intent intent = new Intent(AddEventActivity.this,MainActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_completeAddingEvent:
                addEventToDb();
                break;
            case R.id.AddEventActivity_btn_back:
                finish();
                break;
            default:
                break;
        }
    }
}
