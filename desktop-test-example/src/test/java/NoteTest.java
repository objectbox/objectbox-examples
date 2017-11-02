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
    public void testParent() {
        Box<Note> box = store.boxFor(Note.class);
        Note parent = new Note();
        parent.setText("Parent");

        Note note = new Note();
        note.setText("Child");
        note.setParent(parent);
        long id = box.put(note);

        assertTrue(note.getParent().getId() > 0);

        Note noteFromBox = box.get(id);
        assertEquals("Child", noteFromBox.getText());
        assertEquals("Parent", noteFromBox.getParent().getText());
    }
}
