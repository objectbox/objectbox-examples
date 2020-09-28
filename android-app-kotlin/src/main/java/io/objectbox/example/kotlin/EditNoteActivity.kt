package io.objectbox.example.kotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import io.objectbox.example.kotlin.databinding.ActivityAddNoteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteId = intent.getLongExtra(EXTRA_NOTE_ID, -1)

        lifecycleScope.launchWhenCreated {
            // Get list of Author Objects ordered by name.
            val authors = withContext(Dispatchers.IO) {
                ObjectBox.boxStore.boxFor(Author::class.java).query()
                        .order(Author_.name)
                        .build()
                        .find()
            }
            // If given the Object ID, get an existing Note Object to edit.
            val existingNote = withContext(Dispatchers.IO) {
                if (noteId != -1L) {
                    ObjectBox.boxStore.boxFor(Note::class.java)[noteId]
                } else {
                    null
                }
            }
            setUpViews(existingNote, authors)
        }
    }

    private fun setUpViews(existingNote: Note?, authors: List<Author>) {
        ArrayAdapter<Author>(this, android.R.layout.simple_spinner_item, authors).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerAuthor.adapter = it
        }

        // If editing an existing Note, restore its data to the UI.
        existingNote?.also {
            binding.editTextNote.setText(it.text)
            binding.spinnerAuthor.setSelection(authors.indexOf(it.author.target))
        }

        binding.buttonSave.setOnClickListener {
            // Prevent multiple clicks.
            binding.buttonSave.isEnabled = false

            val text = binding.editTextNote.text?.toString()
            if (text.isNullOrBlank()) {
                binding.textInputNote.error = "Note must not be empty"
                binding.buttonSave.isEnabled = true
                return@setOnClickListener
            }

            val selectedAuthor = binding.spinnerAuthor.selectedItem as Author
            lifecycleScope.launch() {
                putNote(text, selectedAuthor, existingNote)
                finish()
            }
        }
    }

    private suspend fun putNote(
            text: String,
            selectedAuthor: Author,
            existingNote: Note?
    ) = withContext(Dispatchers.IO) {
        val note = if (existingNote != null) {
            // Update existing Note Object.
            existingNote.also {
                it.text = text
                it.author.target = selectedAuthor
            }
        } else {
            // Create new Note Object.
            selectedAuthor.writeNote(text)
        }
        // Just put to persist new or updated Note Object.
        ObjectBox.boxStore.boxFor(Note::class.java).put(note)
    }

    companion object {
        private const val EXTRA_NOTE_ID: String = "EXTRA_NOTE_ID"

        fun intent(context: Context, noteId: Long? = null): Intent {
            return Intent(context, EditNoteActivity::class.java).apply {
                if (noteId != null) {
                    putExtra(EXTRA_NOTE_ID, noteId)
                }
            }
        }
    }

}