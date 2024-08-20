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

package io.objectbox.example.android_app_multiprocess

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.objectbox.example.android_app_multiprocess.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Runs on the main process, allows putting some text into the database.
 */
class MainActivity : AppCompatActivity() {

    private var textEntityId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context = applicationContext

        // Create a store instance right away (and clear database).
        (application as MultiProcessApp).applicationScope.launch(Dispatchers.IO) {
            ObjectBox.get(context).removeAllObjects()
        }

        binding.buttonPut.setOnClickListener {
            val text = binding.editText.text.toString()
            if (text.isNullOrBlank()) {
                Toast.makeText(this, "Enter some text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val box = ObjectBox.get(context).boxFor(TextEntity::class.java)
                box.removeAll()
                val id = box.put(TextEntity(text = text))
                withContext(Dispatchers.Main) {
                    textEntityId = id
                    Toast.makeText(context, "Put value $text", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonRead.setOnClickListener {
            startActivity(ReadProcessActivity.intent(this, textEntityId))
        }
    }
}