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
import io.objectbox.android.AndroidScheduler
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription
import java.text.DateFormat
import java.util.*

/** An alternative to [NoteActivity] using a reactive query (without RxJava, just plain ObjectBox API).  */
class ReactiveNoteActivity : Activity() {

    private lateinit var editText: EditText
    private lateinit var addNoteButton: View

    private lateinit var notesBox: Box<Note>
    private lateinit var notesQuery: Query<Note>
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var subscription: DataSubscription

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        setUpViews()

        // using ObjectBox Kotlin extension functions (https://docs.objectbox.io/kotlin-support)
        notesBox = ObjectBox.boxStore.boxFor()

        // query all notes, sorted a-z by their text (https://docs.objectbox.io/queries)
        notesQuery = notesBox.query {
            order(Note_.text)
        }

        // Reactive query (https://docs.objectbox.io/data-observers-and-rx)
        subscription = notesQuery.subscribe().on(AndroidScheduler.mainThread())
                .observer { notes -> notesAdapter.setNotes(notes) }
    }

    override fun onDestroy() {
        subscription.cancel()
        super.onDestroy()
    }

    protected fun setUpViews() {
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
    }

    private val noteClickListener = OnItemClickListener { _, _, position, _ ->
        notesAdapter.getItem(position)?.also {
            notesBox.remove(it)
            Log.d(App.TAG, "Deleted note, ID: " + it.id)
        }
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