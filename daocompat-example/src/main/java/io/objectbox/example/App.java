package io.objectbox.example;

import android.app.Application;

import io.objectbox.BoxStore;

public class App extends Application {

    public static final String TAG = "DaocompatExample";

    private BoxStore boxStore;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        daoSession = new DaoSession(boxStore);
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
