package io.objectbox.example.kotlin

import android.content.Context
import android.util.Log
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.sync.Sync
import java.util.*

/**
 * Singleton to keep BoxStore reference and provide current list of Notes Objects.
 * Inserts demo data if no Objects are stored.
 */
object ObjectBox {

    lateinit var boxStore: BoxStore
        private set

    lateinit var notesLiveData: ObjectBoxLiveData<Note>
        private set

    fun init(context: Context) {
        // On Android make sure to pass a Context when building the Store.
        boxStore = MyObjectBox.builder()
                .androidContext(context.applicationContext)
                .build()

        if (BuildConfig.DEBUG) {
            var syncAvailable = if (Sync.isAvailable()) "available" else "unavailable"
            Log.d(App.TAG,
                    "Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()}, sync $syncAvailable)")
            // Enable Data Browser on debug builds.
            // https://docs.objectbox.io/data-browser
            AndroidObjectBrowser(boxStore).start(context.applicationContext)
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

    fun replaceWithDemoData() {
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

}