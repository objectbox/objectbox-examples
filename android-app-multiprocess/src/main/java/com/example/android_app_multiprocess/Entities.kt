package com.example.android_app_multiprocess

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class TextEntity(
        @Id var id: Long = 0,
        var text: String? = null
)