package io.objectbox.example.kotlin

import android.content.Context
import android.util.Log
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser

/**
 * Singleton to keep BoxStore reference.
 */
object ObjectBox {

    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()

        if (BuildConfig.DEBUG) {
            Log.d(App.TAG, "Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})")
            AndroidObjectBrowser(boxStore).start(context.applicationContext)
        }

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

        boxStore.boxFor(Note::class.java).put(note1, note2, note3)
    }

}