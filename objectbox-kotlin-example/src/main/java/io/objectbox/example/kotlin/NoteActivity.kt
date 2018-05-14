package io.objectbox.example.kotlin

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView.OnItemClickListener
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.query.Query
import java.text.DateFormat
import java.util.*

class NoteActivity : Activity() {

    private lateinit var editText: EditText
    private lateinit var addNoteButton: View

    private lateinit var notesBox: Box<Note>
    private lateinit var notesQuery: Query<Note>
    private lateinit var notesAdapter: NotesAdapter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        setUpViews()

        notesBox = ObjectBox.boxStore.boxFor()

        // query all notes, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
        notesQuery = notesBox.query().order(Note_.text).build()
        updateNotes()
    }

    /** Manual trigger to re-query and update the UI. For a reactive alternative check [ReactiveNoteActivity].  */
    private fun updateNotes() {
        val notes = notesQuery.find()
        notesAdapter.setNotes(notes)
    }

    private fun setUpViews() {
        notesAdapter = NotesAdapter()

        findViewById<ListView>(R.id.listViewNotes).apply {
            adapter = notesAdapter
            onItemClickListener = noteClickListener
        }

        addNoteButton = findViewById<View>(R.id.buttonAdd).apply {
            isEnabled = false
        }

        editText = findViewById<EditText>(R.id.editTextNote).apply {
            setOnEditorActionListener(editorActionListener)
            addTextChangedListener(textChangedListener)
        }
    }

    // Linked from main.xml
    @Suppress("UNUSED_PARAMETER")
    fun onAddButtonClick(view: View) {
        addNote()
    }

    private fun addNote() {
        val noteText = editText.text.toString()
        editText.setText("")

        val df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
        val comment = "Added on " + df.format(Date())

        val note = Note(text = noteText, comment = comment, date = Date())
        notesBox.put(note)
        Log.d(App.TAG, "Inserted new note, ID: " + note.id)

        updateNotes()
    }

    private val noteClickListener = OnItemClickListener { _, _, position, _ ->
        notesAdapter.getItem(position)?.also {
            notesBox.remove(it)
            Log.d(App.TAG, "Deleted note, ID: " + it.id)
        }
        updateNotes()
    }

    private val editorActionListener = TextView.OnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            addNote()
            true
        } else {
            false
        }
    }

    private val textChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            addNoteButton.isEnabled = s.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {
        }
    }
}