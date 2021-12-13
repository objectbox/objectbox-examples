package io.objectbox.example.sync

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }

    companion object {
        const val TAG = "OBXSync"
    }
}