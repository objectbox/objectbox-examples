package io.objectbox.example.sync;

import android.view.View;
import android.widget.CompoundButton;

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
