package com.example.easycalendar;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // The default Realm file is "default.realm" in Context.getFilesDir();
        // we'll change it to "myrealm.realm"
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm1.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}

class MyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm realm = Realm.getDefaultInstance(); // opens "myrealm.realm"
        try {
            // ... Do something ...
        } finally {
            realm.close();
        }
    }
}
