package com.example.feature_notes

import android.content.Context
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.objectbox.query.Query

class NotesRepository(context: Context) {

    private val store: BoxStore
    private val box: Box<Note>
    private val notesByDate: Query<Note>

    init {
        // Set a name unique within the app, this is used to create the database directory.
        store = MyObjectBox.builder()
            .androidContext(context)
            .name("notes")
            .build()
        box = store.boxFor()
        notesByDate = box.query().order(Note_.createdAt).build()
    }

    fun addNote(note: Note) {
        box.put(note)
    }

    fun getAllNotes(): List<Note> {
        return notesByDate.find()
    }

    fun close() {
        store.close();
    }

}
