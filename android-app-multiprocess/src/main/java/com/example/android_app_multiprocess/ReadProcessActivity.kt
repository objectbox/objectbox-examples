package com.example.android_app_multiprocess

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.android_app_multiprocess.databinding.ActivityReadProcessBinding
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

        lifecycleScope.launch(Dispatchers.IO) {
            val box = ObjectBox.get(applicationContext).boxFor(TextEntity::class.java)
            val all = box.all
            withContext(Dispatchers.Main) {
                binding.textViewRead.text = "Read value ${all.firstOrNull()?.text}"
            }
        }
    }

}