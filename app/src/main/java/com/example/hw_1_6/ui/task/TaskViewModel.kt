package com.example.hw_1_6.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hw_1_6.model.Model

class TaskViewModel : ViewModel() {
    private val tasks = mutableListOf<Model>()
    private val _taskList = MutableLiveData<MutableList<Model>>()
    val taskList: LiveData<MutableList<Model>> get() = _taskList

    fun getTasks() {
        _taskList.value = tasks
    }

    fun addTask(task: Model) {
        tasks.add(task)
        _taskList.value = tasks
    }

    fun deleteTask(task: Model) {
        tasks.remove(task)
        _taskList.value = tasks
    }

    fun markTaskAsChecked(task: Model) {
        tasks[task.id].checkBox = true
        getTasks()
    }

    fun updateTask(task: Model) {
        tasks.replaceAll { mTask ->
            when (mTask.id) {
                task.id -> {
                    mTask.task = task.task
                    mTask.title = task.title
                    mTask
                }

                else -> mTask
            }
        }
    }

    fun filterUncheckedTasks() {
        val filteredList = ArrayList<Model>()
        tasks.forEach { task ->
            if (!task.checkBox)
                filteredList.add(task)
        }
        _taskList.value = filteredList
    }

    fun filterCheckedTasks() {
        val filteredList = ArrayList<Model>()
        tasks.forEach { task ->
            if (task.checkBox)
                filteredList.add(task)
        }
        _taskList.value = filteredList
    }
}
