package io.objectbox.example.sync;

import android.app.Application;

public class App extends Application {

    public static final String TAG = "OBXSync";

    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.get().init(this);
    }

}
