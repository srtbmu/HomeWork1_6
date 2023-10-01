package com.example.hw_1_6.ui.task

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hw_1_6.R
import com.example.hw_1_6.databinding.FragmentTaskBinding
import com.example.hw_1_6.model.TaskModel
import com.example.hw_1_6.TaskViewModel
import com.example.hw_1_6.ui.task.adapter.Adapter

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var adapter = Adapter(this::onLongClickTask, this::isCheckedTask, this::onItemClick)
    private var updateTask: TaskModel? = null
    private lateinit var viewModel: TaskViewModel

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
        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.addTaskFragment)
        }
        binding.rvTask.adapter = adapter
        viewModel.list.observe(this) {
            adapter.setTasks(it)
        }
        updateTask = arguments?.getSerializable("updateTask") as TaskModel?

        if (updateTask == null) {
            if (arguments != null) {
                val getTitle = arguments?.getString("key")
                val getTask = arguments?.getString("kay")
                val data = TaskModel(checkBox = false, title = getTitle, task = getTask, id = 0)
                viewModel.addTask(data)
            }
        } else {
            viewModel.upDate(updateTask!!)
        }
        initSpinner()

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

    private fun onItemClick(task: TaskModel) {
        findNavController().navigate(R.id.addTaskFragment, bundleOf("task_key" to task))
    }

    private fun initSpinner() {
        val taskFilterList = arrayOf(
            getString(R.string.all_task),
            getString(R.string.false_task),
            getString(R.string.true_task)
        )

        val adapterSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, taskFilterList)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = adapterSpinner

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {

                when (taskFilterList[position]) {
                    getString(R.string.all_task) -> viewModel.getTasks()
                    getString(R.string.false_task) -> viewModel.filterTasksFalse()
                    getString(R.string.true_task) -> viewModel.filterTasksTrue()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.getTasks()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initSpinner()
    }
}

