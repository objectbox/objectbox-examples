package io.objectbox.example.sync

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Sync
import java.util.*

@Entity
@Sync
data class Task(
    @Id
    var id: Long = 0,
    var text: String? = null,
    var dateCreated: Date? = null,
    var dateFinished: Date? = null
) {

    /**
     * Create note with given text at current time.
     */
    constructor(text: String?) : this(
        text = text,
        dateCreated = Date(),
        dateFinished = Date(0)
    )

    var isDone: Boolean
        get() = dateFinished != null && dateFinished!!.time != 0L
        set(isDone) {
            if (isDone) {
                dateFinished = Date()
            } else {
                dateFinished = Date(0)
            }
        }

}