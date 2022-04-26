package com.example.feature_tasks

import android.content.Context
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.kotlin.boxFor
import io.objectbox.query.Query
import java.util.*

@Entity
data class Task(
    @Id var id: Long = 0,
    var description: String = "",
    var createdAt: Date = Date()
)

class TasksRepository(context: Context) {

    private val store: BoxStore
    private val box: Box<Task>
    private val tasksByDate: Query<Task>

    init {
        store = MyObjectBox.builder()
            .androidContext(context)
            .name("tasks")
            .build()
        box = store.boxFor()
        tasksByDate = box.query().order(Task_.createdAt).build()
    }

    fun addTask(task: Task) {
        box.put(task)
    }

    fun getAllTasks(): List<Task> {
        return tasksByDate.find()
    }

    fun close() {
        store.close();
    }

}
