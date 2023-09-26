package com.example.hw_1_6

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hw_1_6.model.TaskModel

class TaskViewModel : ViewModel() {
    private var tasks = mutableListOf<TaskModel>()
    private var _list = MutableLiveData<MutableList<TaskModel>>()
    var list: LiveData<MutableList<TaskModel>> = _list

    fun addTask(task: TaskModel) {
        tasks.add(task)
        _list.value = tasks
    }

    fun deleteTask(task: TaskModel) {
        tasks.remove(task)
        _list.value = tasks
    }

    fun checkedTask(task: TaskModel, isChecked: Boolean) {
        tasks.replaceAll {
            if (it == task) {
                it.checkBox = isChecked
            } else {
                it
            }
            it
        }
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
}