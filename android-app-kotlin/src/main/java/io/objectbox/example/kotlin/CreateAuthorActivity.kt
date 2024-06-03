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