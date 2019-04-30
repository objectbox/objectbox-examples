package io.objectbox.example;

import android.content.Context;
import android.util.Log;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class ObjectBox {

    private static BoxStore boxStore;

    static void init(Context context) {
        try {
            boxStore = MyObjectBox.builder()
                    .androidContext(context.getApplicationContext())
                    .build();

            if (BuildConfig.DEBUG) {
                new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
                Log.d(App.TAG, String.format("Using ObjectBox %s (%s)",
                        BoxStore.getVersion(), BoxStore.getVersionNative()));
            }
        } catch (LinkageError e) {
            boxStore = null;
            Log.e(App.TAG, "Failed to load ObjectBox: " + e.getMessage());
        }
    }

    public static BoxStore get() {
        return boxStore;
    }
}
