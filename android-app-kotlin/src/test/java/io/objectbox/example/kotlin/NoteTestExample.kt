package io.objectbox.example.kotlin

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class NoteTestExample : AbstractObjectBoxTest() {

    private val testAuthor = Author(
        name = "Sarah"
    )

    private val testNote = Note(
        text = "Test note text.",
        date = Date()
    ).apply {
        author.target = testAuthor
    }

    @Test
    fun exampleTest() {
        // Get a box and use ObjectBox as usual
        val noteBox = store.boxFor(Note::class.java)

        noteBox.put(testNote)

        val dbNote = noteBox.query().build().findFirst()!!
        assertEquals(testNote.text, dbNote.text)
        assertEquals(testNote.date!!.time, dbNote.date!!.time)
        assertEquals(testAuthor.name, dbNote.author.target.name)
    }

}