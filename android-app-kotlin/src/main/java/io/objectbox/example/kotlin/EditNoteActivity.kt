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

package io.objectbox.example.kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.objectbox.example.kotlin.databinding.ActivityAddNoteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteId = intent.getLongExtra(EXTRA_NOTE_ID, -1)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
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
        noteText: String,
        selectedAuthor: Author,
        existingNote: Note?
    ) = withContext(Dispatchers.IO) {
        val note = if (existingNote != null) {
            // Update existing Note Object.
            existingNote.apply {
                text = noteText
                author.target = selectedAuthor
            }
        } else {
            // Create new Note Object.
            // Keep the default value for `id` (0) to signal this is a new Object.
            // After put the `id` has changed to the Object ID of the stored Note.
            Note(text = noteText, date = Date()).apply {
                author.target = selectedAuthor
            }
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