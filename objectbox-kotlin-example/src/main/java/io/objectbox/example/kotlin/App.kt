package io.objectbox.example.kotlin

import android.app.Application
import android.os.Environment

import java.io.File

import io.objectbox.BoxStore

class App : Application() {

    companion object Constants {
        const val TAG = "ObjectBoxExample"
        const val EXTERNAL_DIR = false
    }

    lateinit var boxStore: BoxStore
        private set

    override fun onCreate() {
        super.onCreate()

        //        if (EXTERNAL_DIR) {
        //            // Example how you could use a custom dir in "external storage"
        //            // (Android 6+ note: give the app storage permission in app info settings)
        //            File directory = new File(Environment.getExternalStorageDirectory(), "objectbox-notes");
        //            boxStore = MyObjectBox.builder().androidContext(App.this).directory(directory).build();
        //        } else {
        // This is the minimal setup required on Android
        boxStore = MyObjectBox.builder().androidContext(this).build()
        //        }
    }

}
