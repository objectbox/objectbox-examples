package io.objectbox.example.sync

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.objectbox.BoxStore
import io.objectbox.BoxStoreBuilder
import io.objectbox.android.AndroidObjectBrowser
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.exception.DbException
import io.objectbox.exception.FileCorruptException
import io.objectbox.exception.PagesCorruptException
import io.objectbox.kotlin.boxFor
import io.objectbox.model.ValidateOnOpenMode
import io.objectbox.sync.Sync
import io.objectbox.sync.SyncChange
import io.objectbox.sync.SyncCredentials
import io.objectbox.sync.listener.SyncChangeListener
import io.objectbox.sync.listener.SyncLoginListener
import java.io.File
import java.util.*
import java.util.zip.GZIPOutputStream

/**
 * Singleton to keep BoxStore reference and provide current list of Notes Objects.
 * Inserts demo data if no Objects are stored.
 */
object ObjectBox {

    private const val SYNC_SERVER_URL = "ws://10.0.2.2"

    private lateinit var boxStore: BoxStore
    private val syncChangesLiveData = MutableLiveData<List<SyncChange>>()

    fun init(context: Context) {
        val storeBuilder = MyObjectBox.builder()
            .name("tasks-synced")
            .validateOnOpen(ValidateOnOpenMode.WithLeaves) // Additional DB page validation
            .validateOnOpenPageLimit(20)
            .androidContext(context.applicationContext)
        boxStore = try {
            storeBuilder.build()
        } catch (e: FileCorruptException) {
            // Demonstrate handling issues caused by devices with a broken file system
            Log.w(App.TAG, "File corrupt, trying previous data snapshot...", e)
            // Retrying requires ObjectBox 2.7.1+
            storeBuilder.usePreviousCommit()
            storeBuilder.build()
        }

        Log.i(App.TAG, "Starting client with: $SYNC_SERVER_URL")
        val loginListener = object : SyncLoginListener {
            override fun onLoggedIn() {
                Log.i(App.TAG, "logged in")
            }

            override fun onLoginFailed(syncLoginCode: Long) {}
        }

        // Note: given BoxStore keeps a reference to Sync client
        Sync.client(boxStore, SYNC_SERVER_URL, SyncCredentials.none())
            .changeListener { syncChanges: Array<SyncChange> ->
                Log.i(App.TAG, "Changes: " + syncChanges.size)
                syncChangesLiveData.postValue(syncChanges.toList())
            }
            .loginListener(loginListener)
            .buildAndStart()

        // Enable Data Browser on debug builds.
        // https://docs.objectbox.io/data-browser
        if (BuildConfig.DEBUG) {
            Log.i(
                App.TAG,
                "Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})"
            )
            AndroidObjectBrowser(boxStore).start(context.applicationContext)
        }
    }

    fun getTasksLiveData(filter: TasksFilter): LiveData<List<Task?>> {
        val builder = boxStore.boxFor(Task::class).query()
        when (filter) {
            TasksFilter.OPEN -> {
                builder.apply(Task_.dateFinished.equal(0))
                builder.orderDesc(Task_.dateCreated)
            }
            TasksFilter.DONE -> {
                builder.apply(Task_.dateFinished.notEqual(0))
                builder.orderDesc(Task_.dateFinished)
            }
            else -> builder.orderDesc(Task_.dateCreated)
        }
        return ObjectBoxSyncLiveData(builder.build(), syncChangesLiveData)
    }

    fun addTask(text: String) {
        boxStore.boxFor(Task::class).put(Task(text))
    }

    fun removeTask(id: Long) {
        boxStore.boxFor(Task::class).remove(id)
    }

    fun changeTaskDone(task: Task, isDone: Boolean) {
        task.isDone = isDone
        boxStore.boxFor(Task::class).put(task)
    }

}