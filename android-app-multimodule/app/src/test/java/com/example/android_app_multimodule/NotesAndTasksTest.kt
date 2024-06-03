/*
 * Copyright 2024 ObjectBox Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        // For this test to run, an ObjectBox library that matches the platform
        // of your development machine must be added to test dependencies.
        // See the build.gradle.kts file for how this looks like.

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