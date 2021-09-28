package io.objectbox.example.sync

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import io.objectbox.example.sync.databinding.ActivityTasksBinding

class TasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTasksBinding
    private val model by viewModels<TasksViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TasksAdapter(itemClickListener)
        model.filteredTasks.observe(this, { list: List<Task?> -> adapter.submitList(list) })

        binding.recyclerview.also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        val tabs = binding.tabs
        val lastFilter = model.filter.value
        addTab(tabs, lastFilter, TasksFilter.ALL)
        addTab(tabs, lastFilter, TasksFilter.OPEN)
        addTab(tabs, lastFilter, TasksFilter.DONE)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                model.filter.postValue(TasksFilter.fromId(tab.id))
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.buttonAdd.setOnClickListener {
            val text = binding.textInputLayout.editText!!.text
            if (text.isNullOrEmpty()) {
                binding.textInputLayout.error = "Text must not be empty"
                return@setOnClickListener
            }
            // Clear text and error
            binding.textInputLayout.error = null
            binding.textInputLayout.editText!!.setText(null)
            ObjectBox.addTask(text.toString())
        }
    }

    private fun addTab(tabs: TabLayout, lastFilter: TasksFilter?, filter: TasksFilter) {
        tabs.addTab(
            tabs.newTab().setText(filter.textResId).setId(filter.id),
            filter == lastFilter
        )
    }

    private val itemClickListener = object : TasksAdapter.ClickListener {
        override fun onCheckedClick(task: Task, isChecked: Boolean) {
            ObjectBox.changeTaskDone(task, isChecked)
        }

        override fun onRemoveClick(task: Task) {
            ObjectBox.removeTask(task.id)
        }
    }
}