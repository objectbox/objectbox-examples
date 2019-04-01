package io.objectbox.example;

import android.content.Context;
import android.util.Log;

import io.objectbox.BoxStore;
import io.objectbox.example.daocompat.BuildConfig;

public class ObjectBox {

    private static BoxStore boxStore;
    private static DaoSession daoSession;

    static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
        daoSession = new DaoSession(boxStore);

        if (BuildConfig.DEBUG) {
            Log.d(App.TAG, String.format("Using ObjectBox %s (%s)",
                    BoxStore.getVersion(), BoxStore.getVersionNative()));
        }
    }

    // Use for any new database code
    public static BoxStore get() {
        return boxStore;
    }

    // Use with existing greenDAO code
    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
