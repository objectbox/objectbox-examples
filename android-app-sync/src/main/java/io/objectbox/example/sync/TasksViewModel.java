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
