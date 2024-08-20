/*
 * Copyright 2024 ObjectBox Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.objectbox.example.android_app_multiprocess

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import io.objectbox.BoxStore

/**
 * Singleton to keep BoxStore reference.
 */
abstract class ObjectBoxSingleton {

    @Volatile
    private var boxStore: BoxStore? = null

    /**
     * Note: when using multiple processes, do not call this from Application.onCreate()
     * as a new Application is created for every new process.
     */
    fun get(context: Context): BoxStore {
        // Double-checked locking, adapted from Kotlin lazy implementation.
        val existing = boxStore
        if (existing != null) { // First check (no locking)
            return existing
        }

        return synchronized(this) {
            val existingInLock = boxStore
            if (existingInLock != null) { // Second check (with locking)
                existingInLock
            } else {
                if (BuildConfig.DEBUG) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        Log.d(Constants.TAG, "On process ${Application.getProcessName()}")
                    }
                    Log.d(Constants.TAG, "Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})")
                }

                val newInstance = createBoxStore(context)
                boxStore = newInstance
                newInstance
            }
        }
    }

    abstract fun createBoxStore(context: Context): BoxStore
}

object ObjectBox : ObjectBoxSingleton() {
    override fun createBoxStore(context: Context): BoxStore {
        return MyObjectBox.builder()
                .androidContext(context)
                .build()
    }
}

object ObjectBoxReadOnly : ObjectBoxSingleton() {
    override fun createBoxStore(context: Context): BoxStore {
        return MyObjectBox.builder()
                .androidContext(context)
                .readOnly()
                .build()
    }
}
