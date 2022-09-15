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