package io.objectbox.example.android_app_multiprocess

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.objectbox.example.android_app_multiprocess.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Runs on the main process, allows putting some text into the database.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context = applicationContext

        // Create a store instance right away (and clear database).
        GlobalScope.launch(Dispatchers.IO) {
            ObjectBox.get(context).removeAllObjects()
        }

        binding.buttonPut.setOnClickListener {
            val text = binding.editText.text.toString()
            if (text.isNullOrBlank()) {
                Toast.makeText(this, "Enter some text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            GlobalScope.launch(Dispatchers.IO) {
                val box = ObjectBox.get(context).boxFor(TextEntity::class.java)
                box.removeAll()
                box.put(TextEntity(text = text))
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Put value $text", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonRead.setOnClickListener {
            startActivity(Intent(this, ReadProcessActivity::class.java))
        }
    }
}