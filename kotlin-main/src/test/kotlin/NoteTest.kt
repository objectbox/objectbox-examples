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

import io.objectbox.example.Note
import io.objectbox.kotlin.boxFor
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

class NoteTest : AbstractObjectBoxTest() {
    @Test
    fun testPutAndGet() {
        val box = store.boxFor(Note::class)
        val note = Note(
            text = "Hello Desktop",
            date = Date()
        )
        val id = box.put(note)

        val noteFromBox = box[id]
        assertEquals("Hello Desktop", noteFromBox.text)
    }

    @Test
    fun testParentAndChildren() {
        val box = store.boxFor(Note::class)
        val parent = Note(text = "Parent")

        val note = Note(text = "Child")
        note.setParentNote(parent)
        val childId = box.put(note)

        val parentId = note.getParentNote().id
        assertTrue(parentId > 0)

        val noteFromBox = box[childId]
        assertEquals("Child", noteFromBox.text)
        assertEquals("Parent", noteFromBox.getParentNote().text)

        val parentNoteFromBox = box[parentId]
        assertEquals(1, parentNoteFromBox.children.size)
        assertEquals(childId, parentNoteFromBox.children[0].id)
    }
}