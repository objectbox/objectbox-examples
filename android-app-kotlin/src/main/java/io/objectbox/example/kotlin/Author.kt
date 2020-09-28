package io.objectbox.example.kotlin

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import java.text.DateFormat
import java.util.*

@Entity
data class Author(
        @Id var id: Long = 0,
        var name: String? = null
) {
    @Backlink(to = "author") // 'to' is optional if only one relation matches.
    lateinit var notes: ToMany<Note>

    fun writeNote(text: String): Note {
        val df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
        val comment = "Added on " + df.format(Date())

        return Note(text = text, date = Date(), comment = comment)
                .apply { author.target = this@Author }
    }
}
