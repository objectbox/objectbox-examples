package io.objectbox.example

import io.objectbox.BoxStore
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import java.util.*

@Entity
data class Note(
    @Id var id: Long = 0,
    var text: String? = null,
    var date: Date? = null
) {

    // Note: the ObjectBox Gradle plugin does currently not support
    // "initialization magic" for relations in Kotlin Desktop projects.
    // So ToOne and ToMany fields need to be explicitly initialized,
    // as well as a __boxStore field added.
    // See https://docs.objectbox.io/relations#initialization-magic

    var parent = ToOne<Note>(this, Note_.parent)

    @Backlink
    var children = ToMany<Note>(this, Note_.children)

    @JvmField
    @Transient
    @Suppress("PropertyName")
    var __boxStore: BoxStore? = null

    constructor(text: String?) : this(0, text, Date())

    fun getParentNote(): Note {
        return parent.target
    }

    fun setParentNote(parent: Note?) {
        this.parent.target = parent
    }

}