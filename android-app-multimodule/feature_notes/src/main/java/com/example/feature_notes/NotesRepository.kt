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

package com.example.feature_notes

import android.content.Context
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.objectbox.query.Query

class NotesRepository(context: Context) {

    private val store: BoxStore
    private val box: Box<Note>
    private val notesByDate: Query<Note>

    init {
        // Set a name unique within the app, this is used to create the database directory.
        store = MyObjectBox.builder()
            .androidContext(context)
            .name("notes")
            .build()
        box = store.boxFor()
        notesByDate = box.query().order(Note_.createdAt).build()
    }

    fun addNote(note: Note) {
        box.put(note)
    }

    fun getAllNotes(): List<Note> {
        return notesByDate.find()
    }

    fun close() {
        store.close();
    }

}
