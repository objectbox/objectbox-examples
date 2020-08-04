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