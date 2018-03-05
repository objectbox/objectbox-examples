package io.objectbox.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NoteTestExample {

    private static final File TEST_DIRECTORY = new File("objectbox-example/test-db");
    private static final Note NOTE = new Note();
    static {
        NOTE.setText("Test note text.");
        NOTE.setComment("A test comment");
        NOTE.setDate(new Date());
    }

    private BoxStore store;

    @Before
    public void setUp() throws Exception {
        // delete database files before each test to start with a clean database
        BoxStore.deleteAllFiles(TEST_DIRECTORY);
        store = MyObjectBox.builder()
                // add directory flag to change where ObjectBox puts its database files
                .directory(TEST_DIRECTORY)
                // optional: add debug flags for more detailed ObjectBox log output
                .debugFlags(DebugFlags.LOG_QUERIES | DebugFlags.LOG_QUERY_PARAMETERS)
                .build();
    }

    @After
    public void tearDown() throws Exception {
        if (store != null) {
            store.close();
            store = null;
        }
        BoxStore.deleteAllFiles(TEST_DIRECTORY);
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