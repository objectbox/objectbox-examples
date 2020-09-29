package io.objectbox.example.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import io.objectbox.example.kotlin.databinding.ActivityCreateAuthorBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateAuthorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAuthorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAuthorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener {
            // Prevent multiple clicks.
            binding.buttonSave.isEnabled = false

            val name = binding.editTextAuthor.text?.toString()
            if (name.isNullOrBlank()) {
                binding.editTextAuthor.error = "Author must not be empty"
                binding.buttonSave.isEnabled = true
                return@setOnClickListener
            }

            lifecycleScope.launch() {
                putAuthor(name)
                finish()
            }
        }
    }

    private suspend fun putAuthor(name: String) = withContext(Dispatchers.IO) {
        val newAuthor = Author(name = name)
        ObjectBox.boxStore.boxFor(Author::class.java).put(newAuthor)
    }
}