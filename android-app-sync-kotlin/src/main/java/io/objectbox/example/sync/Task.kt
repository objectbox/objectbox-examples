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

package io.objectbox.example.sync

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Sync
import java.util.*

@Entity
@Sync
data class Task(
    @Id
    var id: Long = 0,
    var text: String? = null,
    var dateCreated: Date? = null,
    var dateFinished: Date? = null
) {

    /**
     * Create a task with the given text at the current time.
     */
    constructor(text: String?) : this(
        text = text,
        dateCreated = Date(),
        dateFinished = Date(0)
    )

    var isDone: Boolean
        get() = dateFinished != null && dateFinished!!.time != 0L
        set(isDone) {
            if (isDone) {
                dateFinished = Date()
            } else {
                dateFinished = Date(0)
            }
        }

}