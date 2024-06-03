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

package io.objectbox.example

import io.objectbox.BoxStore
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import java.util.*

@Entity
data class Note(
    @Id var id: Long = 0,
    var text: String? = null,
    var date: Date? = null
) {

    // Note: the ObjectBox Gradle plugin does currently not support
    // "initialization magic" for relations in Kotlin Desktop projects.
    // So ToOne and ToMany fields need to be explicitly initialized,
    // as well as a __boxStore field added.
    // See https://docs.objectbox.io/relations#initialization-magic

    var parent = ToOne<Note>(this, Note_.parent)

    @Backlink
    var children = ToMany<Note>(this, Note_.children)

    @JvmField
    @Transient
    @Suppress("PropertyName")
    var __boxStore: BoxStore? = null

    constructor(text: String?) : this(0, text, Date())

    fun getParentNote(): Note {
        return parent.target
    }

    fun setParentNote(parent: Note?) {
        this.parent.target = parent
    }

}