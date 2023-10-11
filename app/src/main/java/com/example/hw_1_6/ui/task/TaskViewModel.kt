package com.example.hw_1_6.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hw_1_6.model.TaskModel

class TaskViewModel : ViewModel() {
    private val tasks = mutableListOf<TaskModel>()
    private val _taskList = MutableLiveData<MutableList<TaskModel>>()
    val taskList: LiveData<MutableList<TaskModel>> get() = _taskList

    fun getTasks() {
        _taskList.value = tasks
    }

    fun addTask(task: TaskModel) {
        tasks.add(task)
        _taskList.value = tasks
    }

    fun deleteTask(task: TaskModel) {
        tasks.remove(task)
        _taskList.value = tasks
    }

    fun markTaskAsChecked(task: TaskModel) {
        tasks[task.id].checkBox = true
        getTasks()
    }

    fun updateTask(task: TaskModel) {
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
        val filteredList = ArrayList<TaskModel>()
        tasks.forEach { task ->
            if (!task.checkBox)
                filteredList.add(task)
        }
        _taskList.value = filteredList
    }

    fun filterCheckedTasks() {
        val filteredList = ArrayList<TaskModel>()
        tasks.forEach { task ->
            if (task.checkBox)
                filteredList.add(task)
        }
        _taskList.value = filteredList
    }
}
