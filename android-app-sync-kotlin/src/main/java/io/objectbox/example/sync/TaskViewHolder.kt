package io.objectbox.example.sync

import android.view.View
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