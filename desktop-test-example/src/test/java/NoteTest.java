import org.junit.Test;

import java.util.Date;

import io.objectbox.Box;
import io.objectbox.example.Note;


import static org.junit.Assert.assertEquals;

public class NoteTest extends AbstractObjectBoxTest {
    @Test
    public void test() {
        Box<Note> box = store.boxFor(Note.class);
        Note note = new Note();
        note.setText("Hello Desktop");
        note.setDate(new Date());
        long id = box.put(note);

        Note noteFromBox = box.get(id);
        assertEquals("Hello Desktop", noteFromBox.getText());
    }
}
