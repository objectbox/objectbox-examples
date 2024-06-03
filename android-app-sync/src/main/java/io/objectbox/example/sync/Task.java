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

import java.util.Date;
import java.util.Objects;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Sync;

@Entity
@Sync
public class Task {

    @Id
    private long id;
    private String text;
    private Date dateCreated;
    private Date dateFinished;

    public Task(long id, String text, Date dateCreated, Date dateFinished) {
        this.id = id;
        this.text = text;
        this.dateCreated = dateCreated;
        this.dateFinished = dateFinished;
    }

    /**
     * Create a task with the given text at the current time.
     */
    public Task(String text) {
        this.text = text;
        this.dateCreated = new Date();
        this.dateFinished = new Date(0);
    }

    public Task() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(Date dateFinished) {
        this.dateFinished = dateFinished;
    }

    public void setDone(boolean isDone) {
        if (isDone) {
            this.dateFinished = new Date();
        } else {
            this.dateFinished = new Date(0);
        }
    }

    public boolean isDone() {
        return dateFinished != null && dateFinished.getTime() != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id
                && Objects.equals(text, task.text)
                && Objects.equals(dateCreated, task.dateCreated)
                && Objects.equals(dateFinished, task.dateFinished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, dateCreated, dateFinished);
    }
}
