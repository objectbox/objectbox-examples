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

import androidx.annotation.StringRes
import io.objectbox.example.sync.R

enum class TasksFilter(
    val id: Int,
    @param:StringRes val textResId: Int
) {
    ALL(1, R.string.tab_all),
    OPEN(2, R.string.tab_open),
    DONE(3, R.string.tab_done);

    companion object {
        fun fromId(id: Int): TasksFilter {
            return values().find { it.id == id } ?: ALL
        }
    }
}