package com.example.feature_notes

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
data class Note(
    @Id var id: Long = 0,
    var text: String = "",
    var createdAt: Date = Date()
)