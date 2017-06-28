package io.objectbox.example.kotlin


import java.util.Date

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Generated
import io.objectbox.annotation.Id
import io.objectbox.annotation.apihint.Internal

@Entity
data class Note(
        @Id var id: Long = 0,
        var text: String? = null,
        var comment: String? = null,
        var date: Date? = null
)
