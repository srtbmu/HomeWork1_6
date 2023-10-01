package com.example.hw_1_6.ui.task.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hw_1_6.databinding.ItemTaskBinding
import com.example.hw_1_6.model.TaskModel

class Adapter(
    private var onLongClick: (TaskModel) -> Unit,
    private var checkedTask: (TaskModel, Boolean) -> Unit,
    private var onclick: (TaskModel) -> Unit,
) : Adapter<com.example.hw_1_6.ui.task.adapter.Adapter.TaskHolder>() {

    private var taskList = mutableListOf<TaskModel>()

    fun setTasks(newList: MutableList<TaskModel>) {
        this.taskList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        return TaskHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = taskList.size


    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(taskList.get(position))
    }

    inner class TaskHolder(private var binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun bind(taskModel: TaskModel) {
            with(binding) {
                taskModel.id=adapterPosition
                title.text = taskModel.title
                task.text = taskModel.task
                checkbox.isChecked = taskModel.checkBox

                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    checkedTask(taskModel, isChecked)
                    Log.e("ololo", "bind:$isChecked", )
                }
            }
            itemView.setOnLongClickListener {
                onLongClick(taskModel)
                true
            }
            itemView.setOnClickListener {
                onclick(taskModel)
            }
        }
    }
}