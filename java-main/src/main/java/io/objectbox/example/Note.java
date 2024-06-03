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

import java.util.Date;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Note {

    @Id
    long id;

    String text;
    Date date;
    ToOne<Note> parent;

    @Backlink
    ToMany<Note> children;

    // A (optional) constructor with all properties helps ObjectBox
    // to construct objects faster.
    public Note(long id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public Note() {
    }

    public Note(String text) {
        this.text = text;
        date = new Date();
    }

    public long getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Note getParent() {
        return parent.getTarget();
    }

    public void setParent(Note parent) {
        this.parent.setTarget(parent);
    }

    public ToMany<Note> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "Note \"" + text + "\" on " + date;
    }
}
