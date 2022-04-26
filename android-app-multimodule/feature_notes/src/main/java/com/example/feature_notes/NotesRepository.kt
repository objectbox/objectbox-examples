package com.example.feature_notes

import android.content.Context
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.kotlin.boxFor
import io.objectbox.query.Query
import java.util.*

@Entity
data class Note(
    @Id var id: Long = 0,
    var text: String = "",
    var createdAt: Date = Date()
)

class NotesRepository(context: Context) {

    private val store: BoxStore
    private val box: Box<Note>
    private val notesByDate: Query<Note>

    init {
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
