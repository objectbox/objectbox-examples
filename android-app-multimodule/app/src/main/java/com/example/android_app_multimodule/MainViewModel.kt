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