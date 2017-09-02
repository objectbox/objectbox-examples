package io.objectbox.example.relation;

import android.app.Application;

import io.objectbox.BoxStore;

public class App extends Application {

    public static final String TAG = "Relations";

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
