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

import io.objectbox.BoxStore
import io.objectbox.config.DebugFlags
import org.junit.After
import org.junit.Before
import java.io.File

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