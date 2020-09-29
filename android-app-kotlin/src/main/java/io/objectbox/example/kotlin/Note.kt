package io.objectbox.example.kotlin

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.relation.ToOne
import java.util.*

@Entity // Signals ObjectBox to create a Box for this class.
data class Note(
        // Every @Entity requires a Long @Id property.
        // The default value 0 signals that this is a new Object.
        @Id
        var id: Long = 0,

        var text: String? = null,

        @Index // Improves query performance at the cost of storage space.
        var date: Date? = null
) {
    // To-one relation to an Author Object.
    // https://docs.objectbox.io/relations#to-one-relations
    lateinit var author: ToOne<Author>
}