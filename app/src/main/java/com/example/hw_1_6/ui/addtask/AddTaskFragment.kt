package com.example.hw_1_6.ui.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hw_1_6.R
import com.example.hw_1_6.databinding.FragmentAddTaskBinding
import com.example.hw_1_6.model.TaskModel

class AddTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments == null) {
            initClicker()
        } else {
            update()
        }

    }

    private fun initClicker() {
        binding.btnAdd.setOnClickListener {
            var title = binding.etTitle.text.toString()
            var task = binding.etTask.text.toString()
            findNavController().navigate(R.id.taskFragment, bundleOf("key" to title, "kay" to task))
        }
    }

    private fun update() {
        var updateTitle = arguments?.getSerializable("task_key") as TaskModel?
        binding.etTitle.setText(updateTitle?.title)
        binding.etTask.setText(updateTitle?.task)

        binding.btnAdd.text = "update"
        binding.btnAdd.setOnClickListener {
            val data = updateTitle!!.copy(
                title = binding.etTitle.text.toString(),
                task = binding.etTask.text.toString()
            )
            findNavController().navigate(R.id.taskFragment, bundleOf("updateTask" to data))
        }
    }

}