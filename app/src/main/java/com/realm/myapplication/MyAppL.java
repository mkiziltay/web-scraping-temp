package com.realm.myapplication;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyAppL extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myFoodB")//Throws exception if identified twice...
                .allowWritesOnUiThread(true) // this block allow for deletion.
                .build();
        Realm.setDefaultConfiguration(config);

    }
}
