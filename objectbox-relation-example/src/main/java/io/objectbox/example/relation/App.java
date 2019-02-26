package io.objectbox.example.relation;

import android.app.Application;

public class App extends Application {

    public static final String TAG = "Relations";

    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
    }

}
