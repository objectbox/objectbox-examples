package io.objectbox.example.sync;

import android.os.Bundle;
import android.text.Editable;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.objectbox.example.sync.databinding.ActivityTasksBinding;

public class TasksActivity extends AppCompatActivity {

    private ActivityTasksBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTasksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TasksViewModel model = new ViewModelProvider(this).get(TasksViewModel.class);

        TasksAdapter adapter = new TasksAdapter(itemClickListener);
        model.filteredTasks.observe(this, adapter::submitList);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(adapter);

        TabLayout tabs = binding.tabs;
        TasksFilter lastFilter = model.filter.getValue();
        addTab(tabs, lastFilter, TasksFilter.ALL);
        addTab(tabs, lastFilter, TasksFilter.OPEN);
        addTab(tabs, lastFilter, TasksFilter.DONE);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                model.filter.postValue(TasksFilter.fromId(tab.getId()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.buttonAdd.setOnClickListener(v -> {
            Editable text = binding.textInputLayout.getEditText().getText();
            if (text == null || text.length() == 0) {
                binding.textInputLayout.setError("Text must not be empty");
                return;
            }
            // Clear text and error
            binding.textInputLayout.setError(null);
            binding.textInputLayout.getEditText().setText(null);
            ObjectBox.get().addTask(text.toString());
        });
    }

    private void addTab(TabLayout tabs, TasksFilter lastFilter, TasksFilter filter) {
        tabs.addTab(tabs.newTab().setText(filter.textResId).setId(filter.id), filter == lastFilter);
    }

    private final TasksAdapter.ClickListener itemClickListener = new TasksAdapter.ClickListener() {
        @Override
        public void onCheckedClick(@NonNull Task task, boolean isChecked) {
            ObjectBox.get().changeTaskDone(task, isChecked);
        }

        @Override
        public void onRemoveClick(@NonNull Task task) {
            ObjectBox.get().removeTask(task.getId());
        }
    };
}