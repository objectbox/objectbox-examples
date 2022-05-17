package com.example.android_app_multimodule

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.feature_notes.Note
import com.example.feature_notes.NotesRepository
import com.example.feature_tasks.Task
import com.example.feature_tasks.TasksRepository
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class NotesAndTasksTest {

    val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun putAndGet_works() {
        val notesRepository = NotesRepository(context)
        val tasksRepository = TasksRepository(context)

        val testNote = Note(text = "This is a note")
        notesRepository.addNote(testNote)
        val testTask = Task(description = "This is a task")
        tasksRepository.addTask(testTask)

        val notes = notesRepository.getAllNotes()
        assertEquals(testNote.text, notes[0].text)
        val tasks = tasksRepository.getAllTasks()
        assertEquals(testTask.description, tasks[0].description)

        notesRepository.close()
        tasksRepository.close()

        // Note: not deleting database files as Robolectric uses a unique
        // directory in the systems temporary directory unique between tests.
    }

}