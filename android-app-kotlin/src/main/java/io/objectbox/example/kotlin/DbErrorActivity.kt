package io.objectbox.example.kotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withStarted
import io.objectbox.example.kotlin.databinding.ActivityDbErrorBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Locale

/**
 * Activity to inform the user the database is corrupted,
 * can share a copy of the database file via email.
 */
class DbErrorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDbErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDbErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSendDbFile.setOnClickListener {
            sendDatabaseFileViaEmail()
        }
    }

    private fun sendDatabaseFileViaEmail() = lifecycleScope.launch {
        val context = applicationContext

        val intent = withContext(Dispatchers.IO) {
            val dbFileCopy = File(filesDir, "broken-db/data.mdb.gz")
            val success = ObjectBox.copyAndGzipDatabaseFileTo(dbFileCopy, context)
            if (!success) {
                return@withContext null
            }

            // Get a URI for the copied database file using a FileProvider
            // https://developer.android.com/reference/androidx/core/content/FileProvider
            val dbFileUri = FileProvider.getUriForFile(
                context,
                "io.objectbox.example.kotlin.fileprovider",
                dbFileCopy
            )

            // Add the exception message and some helpful device info to the email body.
            val message = "Error message: ${ObjectBox.dbExceptionMessage}\n" +
                    "Device info: ${Build.MANUFACTURER.uppercase(Locale.US)} ${Build.MODEL}, " +
                    "Android ${Build.VERSION.RELEASE}\n\n"

            // Email with one attachment
            // https://developer.android.com/guide/components/intents-common#Email
            Intent(Intent.ACTION_SEND)
                .setType("*/*")
                .putExtra(Intent.EXTRA_EMAIL, arrayOf("support@example.com"))
                .putExtra(Intent.EXTRA_SUBJECT, "Database error report")
                .putExtra(Intent.EXTRA_TEXT, message)
                .putExtra(Intent.EXTRA_STREAM, dbFileUri)
        }
        withContext(Dispatchers.Main) {
            withStarted {
                if (intent != null) {
                    startActivity(
                        Intent.createChooser(intent, getString(R.string.db_error_send))
                    )
                } else {
                    Toast.makeText(context, "Database in use, can not copy.", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

}