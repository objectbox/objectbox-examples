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

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.relation.ToOne
import java.util.Date

@Entity // Signals ObjectBox to create a Box for this class.
data class Note(
        // Every @Entity requires a Long @Id property.
        // The default value 0 signals that this is a new Object.
        @Id
        var id: Long = 0,

        var text: String? = null,

        @Index // Improves query performance at the cost of storage space.
        var date: Date? = null
) {
    // To-one relation to an Author Object.
    // https://docs.objectbox.io/relations#to-one-relations
    lateinit var author: ToOne<Author>
}