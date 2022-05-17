package com.example.feature_tasks

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
data class Task(
    @Id var id: Long = 0,
    var description: String = "",
    var createdAt: Date = Date()
)