package io.objectbox.example.kotlin

import io.objectbox.BoxStore
import io.objectbox.config.DebugFlags
import org.junit.Before
import kotlin.Throws
import org.junit.After
import java.io.File
import java.lang.Exception

/**
 * Android Local Unit test example (https://docs.objectbox.io/android/android-local-unit-tests)
 */
open class AbstractObjectBoxTest {

    private var _store: BoxStore? = null
    protected val store: BoxStore
        get() = _store!!

    @Before
    fun setUp() {
        // Delete any files in the test directory before each test to start with a clean database.
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
        _store = MyObjectBox.builder()
            // Use a custom directory to store the database files in.
            .directory(TEST_DIRECTORY)
            // Optional: add debug flags for more detailed ObjectBox log output.
            .debugFlags(DebugFlags.LOG_QUERIES or DebugFlags.LOG_QUERY_PARAMETERS)
            .build()
    }

    @After
    fun tearDown() {
        _store?.close()
        _store = null
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
    }

    companion object {
        private val TEST_DIRECTORY = File("objectbox-example/test-db")
    }
}