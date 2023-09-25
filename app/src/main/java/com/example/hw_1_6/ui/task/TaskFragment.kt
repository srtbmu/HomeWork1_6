package com.example.hw_1_6.ui.task

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hw_1_6.R
import com.example.hw_1_6.databinding.FragmentTaskBinding
import com.example.hw_1_6.model.TaskModel
import com.example.hw_1_6.model.TaskViewModel
import com.example.hw_1_6.ui.task.adapter.Adapter

class TaskFragment : Fragment(){

    private lateinit var binding: FragmentTaskBinding
    private var adapter = Adapter(this::onLongClickTask,this::isCheckedTask)

    private lateinit var viewModel:TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity())[TaskViewModel::class.java]
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.addTaskFragment)
        }
        binding.rvTask.adapter = adapter
        viewModel.list.observe(this){
            adapter.setTasks(it)
        }
        if(arguments!=null){
            var getTitle=arguments?.getString("key")
            var getTask=arguments?.getString("kay")
            var data=TaskModel(checkBox = false, title = getTitle, task = getTask)
            viewModel.addTask(data)
        }
    }

    private fun onLongClickTask(task: TaskModel) { // deleteTaskClick
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Вы хотите удалить данные?")
            .setMessage("Востановить данные бедет невозможным!")
            .setPositiveButton("ОК") { dialog: DialogInterface, _: Int ->
                // Обработка нажатия кнопки "OK"
                viewModel.deleteTask(task)
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog: DialogInterface, _: Int ->
                // Обработка нажатия кнопки "Отмена"
                dialog.dismiss()
            }
        dialogBuilder.show()
    }

    private fun isCheckedTask(task: TaskModel, isChecked: Boolean) {
        viewModel.checkedTask(task, isChecked)
    }


}

