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

package io.objectbox.example.sync;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import io.objectbox.example.sync.databinding.ItemTaskBinding;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    @NonNull
    private final ItemTaskBinding binding;
    @Nullable
    private Task task;

    public TaskViewHolder(@NonNull ItemTaskBinding binding, @NonNull TasksAdapter.ClickListener clickListener) {
        super(binding.getRoot());
        this.binding = binding;
        // Note: use click listener vs checked change listener to avoid call on binding data.
        binding.checkBoxDone.setOnClickListener(v -> {
            if (task != null) clickListener.onCheckedClick(task, !task.isDone());
        });
        binding.imageButtonRemove.setOnClickListener(v -> {
            if (task != null) clickListener.onRemoveClick(task);
        });
    }

    public void bind(@Nullable Task task) {
        this.task = task;
        if (task == null) {
            binding.textViewText.setText(null);
            binding.checkBoxDone.setChecked(false);
        } else {
            binding.textViewText.setText(task.getText());
            binding.checkBoxDone.setChecked(task.isDone());
        }
    }

}
