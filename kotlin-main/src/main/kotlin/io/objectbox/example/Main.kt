/*
 * Copyright 2022 ObjectBox Ltd. All rights reserved.
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
package io.objectbox.example

import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.objectbox.sync.Sync

/**
 * Note: use the run button in IntelliJ IDEA or Android Studio,
 * or to run with Gradle from a terminal:
 *
 * ```
 * ./gradlew kotlin-main:run --args="This is my note text."
 * ```
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})")
        println("ObjectBox Sync is " + if (Sync.isAvailable()) "available" else "unavailable")

        val store = MyObjectBox.builder().name("objectbox-notes-db").build()
        val box = store.boxFor(Note::class)

        val text = if (args.size > 0) args.joinToString(" ") else "No text given"
        box.put(Note(text))

        println("${box.count()} notes in ObjectBox database:")
        for (note in box.all) {
            println(note)
        }
        store.close()
    }
}