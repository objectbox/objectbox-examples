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

package io.objectbox.example;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.BoxStoreBuilder;
import io.objectbox.query.Query;
import io.objectbox.sync.Sync;
import io.objectbox.sync.SyncChange;
import io.objectbox.sync.SyncClient;
import io.objectbox.sync.SyncCredentials;
import io.objectbox.sync.listener.SyncChangeListener;
import io.objectbox.sync.listener.SyncLoginListener;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TasksSyncDB {

    private final BoxStore tasksBoxStore;
    private final Box<Task> tasksBox;
    private final String syncServerURL = "ws://127.0.0.1";
    private final Logger logger = Logger.getLogger(TasksSyncDB.class.getName());
    private final SyncClient syncClient;

    public TasksSyncDB() {
        logger.log(Level.INFO, String.format("Using ObjectBox %s (%s)", BoxStore.getVersion(), BoxStore.getVersionNative()));
        BoxStoreBuilder storeBuilder = MyObjectBox.builder().name("tasks-synced");
        this.tasksBoxStore = storeBuilder.build();
        this.tasksBox = this.tasksBoxStore.boxFor(Task.class);
        logger.log(Level.INFO, "Starting client with " + syncServerURL);
        this.syncClient = Sync.client(this.tasksBoxStore, syncServerURL, SyncCredentials.none()).loginListener(syncLoginListener).changeListener(syncChangeListener).buildAndStart();
    }

    private final SyncChangeListener syncChangeListener = new SyncChangeListener() {
        @Override
        public void onSyncChanges(SyncChange[] syncChanges) {
            logger.log(Level.INFO, "Changes: " + syncChanges.length);
        }
    };

    private final SyncLoginListener syncLoginListener = new SyncLoginListener() {
        @Override
        public void onLoggedIn() {
            logger.log(Level.INFO, "Log-in success");
        }

        @Override
        public void onLoginFailed(long syncLoginCode) {
            logger.log(Level.SEVERE, "Log-in failure. Error code is " + syncLoginCode);
        }
    };

    public long addTask(String text) {
        Task task = new Task(text);
        return tasksBox.put(task);
    }

    public List<Task> getAll() {
        return tasksBox.getAll();
    }

    public Task getTaskById(long id) {
        return tasksBox.get(id) ;
    }

    public List<Task> getUnfinishedTasks() {
        try (Query<Task> query = tasksBox.query(
                Task_.dateFinished.isNull()
                        .or(Task_.dateFinished.equal(new Date(0)))
            ).build()) {
            return query.find();
        }
    }

    public void completeTask(long id) {
        Task task = tasksBox.get(id);
        task.setDateFinished(new Date());
        tasksBox.put(task);
    }

    public boolean removeTask(long id) {
        return tasksBox.remove(id);
    }

    public void close() {
        this.syncClient.close();
        this.tasksBoxStore.close();
    }

}
