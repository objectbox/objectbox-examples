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

package com.example.feature_tasks

import android.content.Context
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.objectbox.query.Query

class TasksRepository(context: Context) {

    private val store: BoxStore
    private val box: Box<Task>
    private val tasksByDate: Query<Task>

    init {
        // Set a name unique within the app, this is used to create the database directory.
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
