package io.objectbox.example.android_app_multiprocess

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MultiProcessApp : Application() {

    /**
     * For coroutines that shouldn't be cancelled. Uses [Dispatchers.Main].
     * This scope lives as long as the app.
     */
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

}