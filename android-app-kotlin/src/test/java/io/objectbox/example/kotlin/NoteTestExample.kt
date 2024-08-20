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

package io.objectbox.example.kotlin

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class NoteTestExample : AbstractObjectBoxTest() {

    private val testAuthor = Author(
        name = "Sarah"
    )

    private val testNote = Note(
        text = "Test note text.",
        date = Date()
    ).apply {
        author.target = testAuthor
    }

    @Test
    fun exampleTest() {
        // Get a box and use ObjectBox as usual
        val noteBox = store.boxFor(Note::class.java)

        noteBox.put(testNote)

        val dbNote = noteBox.query().build().findFirst()!!
        assertEquals(testNote.text, dbNote.text)
        assertEquals(testNote.date!!.time, dbNote.date!!.time)
        assertEquals(testAuthor.name, dbNote.author.target.name)
    }

}