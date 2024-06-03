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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_notes.Note
import com.example.feature_notes.NotesRepository
import com.example.feature_tasks.Task
import com.example.feature_tasks.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val notesRepository = NotesRepository(application)
    private val tasksRepository = TasksRepository(application)

    fun addExampleData() {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.addNote(Note(text = "This is a note"))
            tasksRepository.addTask(Task(description = "This is a task"))
        }
    }

    override fun onCleared() {
        super.onCleared()
        notesRepository.close()
        tasksRepository.close()
    }

}