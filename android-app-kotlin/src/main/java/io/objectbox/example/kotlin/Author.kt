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
    // Auto-creates a one-to-many relation based on the ToOne in Note.
    // https://docs.objectbox.io/relations#to-many-relations
    @Backlink(to = "author")
    lateinit var notes: ToMany<Note>
}
