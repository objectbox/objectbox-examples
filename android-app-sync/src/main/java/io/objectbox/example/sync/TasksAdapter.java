package io.objectbox.example.sync;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import io.objectbox.example.sync.databinding.ItemTaskBinding;

public class TasksAdapter extends ListAdapter<Task, TaskViewHolder> {

    @NonNull
    private final ClickListener clickListener;

    protected TasksAdapter(@NonNull ClickListener clickListener) {
        super(DIFF_CALLBACK);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(
                ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),
                clickListener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.equals(newItem);
        }
    };

    public static interface ClickListener {
        void onCheckedClick(@NonNull Task task, boolean isChecked);

        void onRemoveClick(@NonNull Task task);
    }
}
