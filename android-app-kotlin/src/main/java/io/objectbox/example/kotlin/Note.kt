package io.objectbox.example.kotlin

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.relation.ToOne
import java.util.*

@Entity
data class Note(
        @Id // Every @Entity requires a Long ID property.
        var id: Long = 0,
        var text: String? = null,
        @Index // Improves query performance at the cost of storage space.
        var date: Date? = null
) {
    lateinit var author: ToOne<Author>
}