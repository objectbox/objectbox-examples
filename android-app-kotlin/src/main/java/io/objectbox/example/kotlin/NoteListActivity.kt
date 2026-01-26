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

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import io.objectbox.Box
import io.objectbox.example.kotlin.databinding.ActivityNoteListBinding
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.launch

class NoteListActivity : AppCompatActivity() {

    private lateinit var notesBox: Box<Note>
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var binding: ActivityNoteListBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If failed to build BoxStore, notify user.
        if (ObjectBox.dbExceptionMessage != null) {
            startActivity(Intent(this, DbErrorActivity::class.java))
            finish()
            return
        }

        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()

        // Using ObjectBox Kotlin extension functions (https://docs.objectbox.io/kotlin-support)
        notesBox = ObjectBox.boxStore.boxFor()

        // See [ObjectBox] on how the Query for this is built.
        // Any changes to an Object in the Note Box will trigger delivery of latest results.
        ObjectBox.notesLiveData.observe(this, Observer {
            notesAdapter.setNotes(it)
        })
    }

    private fun setUpViews() {
        notesAdapter = NotesAdapter()

        binding.listViewNotes.apply {
            adapter = notesAdapter
            onItemClickListener = noteEditListener
            onItemLongClickListener = noteRemoveListener
        }

        binding.buttonAddNote.setOnClickListener {
            startActivity(Intent(this, EditNoteActivity::class.java))
        }

        binding.buttonAddAuthor.setOnClickListener {
            startActivity(Intent(this, CreateAuthorActivity::class.java))
        }
    }

    private val noteRemoveListener = AdapterView.OnItemLongClickListener { _, _, position, _ ->
        notesAdapter.getItem(position)?.also {
            lifecycleScope.launch(ObjectBox.dispatcher) {
                // Pass the Note Object to remove it.
                val removed = notesBox.remove(it)
                // Can also remove by passing an Object ID:
                // notesBox.remove(it.id)

                if (removed) Log.d(App.TAG, "Deleted note, ID: " + it.id)
            }
        }
        true
    }

    private val noteEditListener = OnItemClickListener { _, _, position, _ ->
        notesAdapter.getItem(position)?.also {
            startActivity(EditNoteActivity.intent(this, it.id))
        }
    }

}