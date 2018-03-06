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
        // get a box and use ObjectBox as usual
        Box<Note> noteBox = store.boxFor(Note.class);

        noteBox.put(NOTE);

        Note dbNote = noteBox.query().build().findFirst();
        assertNotNull(dbNote);
        assertEquals(NOTE.text, dbNote.text);
        assertEquals(NOTE.comment, dbNote.comment);
        assertEquals(NOTE.date.getTime(), dbNote.date.getTime());
    }

}