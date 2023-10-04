package com.example.hw_1_6.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hw_1_6.model.TaskModel

class TaskViewModel : ViewModel() {
    private var tasks = mutableListOf<TaskModel>()
    private val _list = MutableLiveData<MutableList<TaskModel>>()
    val list: LiveData<MutableList<TaskModel>> get() = _list

    fun getTasks() {
        _list.value = tasks
    }

    fun addTask(task: TaskModel) {
        tasks.add(task)
        _list.value = tasks
    }

    fun deleteTask(task: TaskModel) {
        tasks.remove(task)
        _list.value = tasks
    }

    fun checkedTask(task: TaskModel) {
        tasks[task.id].checkBox= true
        getTasks()
    }

    fun upDate(task: TaskModel) {
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

    fun filterTasksFalse() {
        val sortedList = ArrayList<TaskModel>()
        tasks.forEach { task ->
            if (task.checkBox == false)
                sortedList.add(task)
        }
        _list.value = sortedList
    }

    fun filterTasksTrue() {

        val sortedList = ArrayList<TaskModel>()
        tasks.forEach { task ->
            if (task.checkBox == true)
                sortedList.add(task)
        }
        _list.value = sortedList
    }

}
