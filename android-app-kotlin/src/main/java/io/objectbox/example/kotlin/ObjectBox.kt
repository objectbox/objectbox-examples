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

package io.objectbox.example.kotlin

import android.content.Context
import android.util.Log
import io.objectbox.BoxStore
import io.objectbox.BoxStoreBuilder
import io.objectbox.android.Admin
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.example.kotlin.ObjectBox.boxStore
import io.objectbox.exception.DbException
import io.objectbox.exception.FileCorruptException
import io.objectbox.kotlin.newFixedThreadPoolDispatcher
import io.objectbox.sync.Sync
import kotlinx.coroutines.CoroutineDispatcher
import java.io.File
import java.util.Date
import java.util.zip.GZIPOutputStream

/**
 * Singleton to keep BoxStore reference and provide current list of Notes Objects.
 * Inserts demo data if no Objects are stored.
 */
object ObjectBox {

    lateinit var boxStore: BoxStore
        private set

    /**
     * If building the [boxStore] failed, contains the thrown error message.
     */
    var dbExceptionMessage: String? = null
        private set

    lateinit var notesLiveData: ObjectBoxLiveData<Note>
        private set

    /**
     * Custom coroutine dispatcher for database operations.
     * It makes sure ObjectBox resources are properly cleaned up.
     */
    val dispatcher: CoroutineDispatcher by lazy {
        boxStore.newFixedThreadPoolDispatcher(4)
    }

    fun init(context: Context) {
        // On Android make sure to pass a Context when building the Store.
        boxStore = try {
            MyObjectBox.builder()
                    .androidContext(context.applicationContext)
                    .build()
        } catch (e: DbException) {
            if (e.javaClass.equals(DbException::class.java) || e is FileCorruptException) {
                // Failed to build BoxStore due to database file issue, store message;
                // checked in NoteListActivity to notify user.
                dbExceptionMessage = e.toString()
                return
            } else {
                // Failed to build BoxStore due to developer error.
                throw e
            }
        }

        if (BuildConfig.DEBUG) {
            var syncAvailable = if (Sync.isAvailable()) "available" else "unavailable"
            Log.d(App.TAG,
                    "Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()}, sync $syncAvailable)")
            // Enable ObjectBox Admin on debug builds.
            // https://docs.objectbox.io/data-browser
            Admin(boxStore).start(context.applicationContext)
        }

        // Prepare a Query for all notes, sorted by their date.
        // The Query is not run until find() is called or
        // it is subscribed to (like ObjectBoxLiveData below does).
        // https://docs.objectbox.io/queries
        val notesQuery = boxStore.boxFor(Note::class.java).query()
                // Sort notes by most recent first.
                .orderDesc(Note_.date)
                // ToOne/ToMany by default is loaded on access,
                // so pre-fetch the ToOne to avoid this happening while view binding.
                .eager(Note_.author)
                .build()

        // Wrap Query in a LiveData that subscribes to it only when there are active observers.
        // If only used by a single activity or fragment, maybe keep this in their ViewModel.
        notesLiveData = ObjectBoxLiveData(notesQuery)

        // Add some demo data if the Boxes are empty.
        if (boxStore.boxFor(Note::class.java).isEmpty
                && boxStore.boxFor(Author::class.java).isEmpty) {
            replaceWithDemoData()
        }
    }

    private fun replaceWithDemoData() {
        val author1 = Author(name = "Alice")
        val author2 = Author(name = "Bob")

        val note1 = author1.writeNote("This is a note for Bob")
        val note2 = author1.writeNote("Write a demo app for ObjectBox")
        val note3 = author2.writeNote("Thanks for your note, Alice")

        // See that each Note above has a new Author set in its ToOne?
        // When the Note is put, its Author will automatically be put into the Author Box.
        // Both ToOne and ToMany automatically put new Objects when the Object owning them is put.
        // But what if the Author is in the Box already?
        // Then just the relation (Object ID) is updated.
        boxStore.boxFor(Note::class.java).put(note1, note2, note3)
    }

    private fun Author.writeNote(text: String): Note {
        return Note(text = text, date = Date()).also {
            it.author.target = this
        }
    }

    /**
     * If the database file is not in use, compresses (GZIP) and copies it to the given [target].
     */
    fun copyAndGzipDatabaseFileTo(target: File, context: Context): Boolean {
        if (BoxStore.isDatabaseOpen(context, null)) {
            // Do not copy if database file is still in use.
            // If it would be open, the copy will likely get corrupted
            // as BoxStore may currently write data to the file.
            Log.e(App.TAG, "Database file is still in use, can not copy.")
            return false
        }

        // If a name was given when building BoxStore use that instead of the default below.
        val dbName = BoxStoreBuilder.DEFAULT_NAME
        File(context.filesDir, "objectbox/$dbName/data.mdb").inputStream().use { input ->
            target.parentFile?.mkdirs()
            GZIPOutputStream(target.outputStream()).use { output ->
                input.copyTo(output, DEFAULT_BUFFER_SIZE)
            }
        }
        return true
    }

}