package io.objectbox.example.sync;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class TasksViewModel extends AndroidViewModel {

    public final MutableLiveData<TasksFilter> filter = new MutableLiveData<>();
    public final LiveData<List<Task>> filteredTasks;

    public TasksViewModel(@NonNull Application application) {
        super(application);
        filter.setValue(TasksFilter.ALL);
        filteredTasks = Transformations.switchMap(
                filter,
                filter -> ObjectBox.get().getTasksLiveData(filter)
        );
    }
}
