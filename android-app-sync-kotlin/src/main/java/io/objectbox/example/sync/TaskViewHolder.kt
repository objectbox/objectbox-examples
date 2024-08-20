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

import androidx.recyclerview.widget.RecyclerView
import io.objectbox.example.sync.databinding.ItemTaskBinding

class TaskViewHolder(
    private val binding: ItemTaskBinding,
    clickListener: TasksAdapter.ClickListener
) : RecyclerView.ViewHolder(
    binding.root
) {
    private var task: Task? = null

    init {
        // Note: use click listener vs checked change listener to avoid call on binding data.
        binding.checkBoxDone.setOnClickListener {
            task?.let { clickListener.onCheckedClick(it, !it.isDone) }
        }
        binding.imageButtonRemove.setOnClickListener {
            task?.let { clickListener.onRemoveClick(it) }
        }
    }

    fun bind(task: Task?) {
        this.task = task
        if (task == null) {
            binding.textViewText.text = null
            binding.checkBoxDone.isChecked = false
        } else {
            binding.textViewText.text = task.text
            binding.checkBoxDone.isChecked = task.isDone
        }
    }
}