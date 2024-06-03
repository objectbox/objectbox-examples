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

import org.junit.Test;

import java.util.Date;

import io.objectbox.Box;
import io.objectbox.example.Note;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NoteTest extends AbstractObjectBoxTest {

    @Test
    public void testPutAndGet() {
        Box<Note> box = store.boxFor(Note.class);
        Note note = new Note();
        note.setText("Hello Desktop");
        note.setDate(new Date());
        long id = box.put(note);

        Note noteFromBox = box.get(id);
        assertEquals("Hello Desktop", noteFromBox.getText());
    }

    @Test
    public void testParentAndChildren() {
        Box<Note> box = store.boxFor(Note.class);
        Note parent = new Note();
        parent.setText("Parent");

        Note note = new Note();
        note.setText("Child");
        note.setParent(parent);
        long childId = box.put(note);

        long parentId = note.getParent().getId();
        assertTrue(parentId > 0);

        Note noteFromBox = box.get(childId);
        assertEquals("Child", noteFromBox.getText());
        assertEquals("Parent", noteFromBox.getParent().getText());

        Note parentNoteFromBox = box.get(parentId);
        assertEquals(1, parentNoteFromBox.getChildren().size());
        assertEquals(childId, parentNoteFromBox.getChildren().get(0).getId());
    }
}
