package io.objectbox.example.sync

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap

class TasksViewModel(application: Application) : AndroidViewModel(application) {

    val filter = MutableLiveData<TasksFilter>()
    val filteredTasks: LiveData<List<Task?>>

    init {
        filter.value = TasksFilter.ALL
        filteredTasks = filter.switchMap { filter: TasksFilter ->
            ObjectBox.getTasksLiveData(filter)
        }
    }
}