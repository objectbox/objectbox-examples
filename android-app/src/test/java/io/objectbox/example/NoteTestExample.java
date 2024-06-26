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

import org.junit.Test;

import java.util.Date;

import io.objectbox.Box;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NoteTestExample extends AbstractObjectBoxTest {

    private static final Note NOTE = new Note();
    static {
        NOTE.setText("Test note text.");
        NOTE.setComment("A test comment");
        NOTE.setDate(new Date());
    }

    @Test
    public void exampleTest() {
        // Get a box and use ObjectBox as usual
        Box<Note> noteBox = store.boxFor(Note.class);

        noteBox.put(NOTE);

        Note dbNote = noteBox.query().build().findFirst();
        assertNotNull(dbNote);
        assertEquals(NOTE.text, dbNote.text);
        assertEquals(NOTE.comment, dbNote.comment);
        assertEquals(NOTE.date.getTime(), dbNote.date.getTime());
    }

}