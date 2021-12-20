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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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
            lifecycleScope.launch(Dispatchers.IO) {
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