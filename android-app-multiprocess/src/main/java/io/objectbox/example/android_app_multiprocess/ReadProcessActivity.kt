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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.objectbox.example.android_app_multiprocess.databinding.ActivityReadProcessBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * This activity runs in a separate process (see AndroidManifest.xml) and
 * reads some data from the database.
 */
class ReadProcessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityReadProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textEntityId = intent.getLongExtra(EXTRA_TEXT_ENTITY_ID, 0)

        lifecycleScope.launch(Dispatchers.IO) {
            val textEntity = if (textEntityId > 0) {
                val box = ObjectBox.get(applicationContext).boxFor(TextEntity::class.java)
                box.get(textEntityId)
            } else null
            withContext(Dispatchers.Main) {
                binding.textViewRead.text = "Read value ${textEntity?.text}"
            }
        }
    }

    companion object {
        const val EXTRA_TEXT_ENTITY_ID = "EXTRA_TEXT_ENTITY_ID"
        fun intent(context: Context, textEntityId: Long): Intent {
            return Intent(context, ReadProcessActivity::class.java)
                    .putExtra(EXTRA_TEXT_ENTITY_ID, textEntityId)
        }
    }

}