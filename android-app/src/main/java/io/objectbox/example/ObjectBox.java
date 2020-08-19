package io.objectbox.example;

import android.content.Context;
import android.util.Log;

import io.objectbox.BoxStore;
import io.objectbox.BoxStoreBuilder;
import io.objectbox.android.AndroidObjectBrowser;
import io.objectbox.exception.FileCorruptException;

public class ObjectBox {

    private static BoxStore boxStore;

    static void init(Context context) {
        BoxStoreBuilder storeBuilder = MyObjectBox.builder()
                .androidContext(context.getApplicationContext());
        try {
            boxStore = storeBuilder.build();
        } catch (FileCorruptException e) { // Demonstrate handling issues caused by devices with a broken file system
            Log.w(App.TAG, "File corrupt, trying previous data snapshot...", e);
            // Retrying requires ObjectBox 2.7.1+
            storeBuilder.usePreviousCommit();
            boxStore = storeBuilder.build();
        }

        if (BuildConfig.DEBUG) {
            Log.d(App.TAG, String.format("Using ObjectBox %s (%s)",
                    BoxStore.getVersion(), BoxStore.getVersionNative()));
            new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
        }
    }

    public static BoxStore get() {
        return boxStore;
    }
}
