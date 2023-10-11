package com.example.hw_1_6.ui.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.hw_1_6.databinding.ItemTaskBinding
import com.example.hw_1_6.model.Model

class Adapter(
    private var onLongClick: (Model) -> Unit,
    val onDoneClick: (Model)->Unit,
    private var onclick: (Model) -> Unit,
) : Adapter<com.example.hw_1_6.ui.task.adapter.Adapter.TaskHolder>() {

    private var taskList = mutableListOf<Model>()

    fun setTasks(newList: MutableList<Model>) {
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
        fun bind(taskModel: Model) {
            with(binding) {
                taskModel.id=adapterPosition
                textTitle.text = taskModel.title
                textTask.text = taskModel.task
                checkbox.isChecked = taskModel.checkBox

                checkbox.setOnClickListener {
                    onDoneClick(taskModel)
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