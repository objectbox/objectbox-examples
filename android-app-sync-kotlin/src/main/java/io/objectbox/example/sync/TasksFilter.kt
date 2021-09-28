package io.objectbox.example.sync

import androidx.annotation.StringRes
import io.objectbox.example.sync.R

enum class TasksFilter(
    val id: Int,
    @param:StringRes val textResId: Int
) {
    ALL(1, R.string.tab_all),
    OPEN(2, R.string.tab_open),
    DONE(3, R.string.tab_done);

    companion object {
        fun fromId(id: Int): TasksFilter {
            return values().find { it.id == id } ?: ALL
        }
    }
}