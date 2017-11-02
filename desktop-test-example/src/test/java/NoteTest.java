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
