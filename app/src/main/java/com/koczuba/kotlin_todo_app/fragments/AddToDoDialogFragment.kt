package com.koczuba.kotlin_todo_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.koczuba.kotlin_todo_app.databinding.FragmentAddToDoDialogBinding
import com.koczuba.kotlin_todo_app.utils.model.ToDoData

class AddToDoDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentAddToDoDialogBinding
    private var listener: OnDialogAddTaskButtonClickListener? = null
    private var toDoData: ToDoData? = null

    fun setListener(listener: OnDialogAddTaskButtonClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddToDoDialogFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) =
            AddToDoDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("taskId", taskId)
                    putString("task", task)
                }
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddToDoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {

            toDoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString()
            )
            binding.todoTaskEt.setText(toDoData?.taskDescription)
        }

        registerEvents()

    }

    private fun registerEvents() {

        binding.addTaskButton.setOnClickListener {
            val todoTask = binding.todoTaskEt.text.toString()
            if (todoTask.isEmpty()) {
                return@setOnClickListener
            }
            if (toDoData == null) {
                listener?.saveTask(todoTask, binding.todoTaskEt)
            } else {
                toDoData!!.taskDescription = todoTask
                listener?.updateTask(toDoData!!, binding.todoTaskEt)
            }
        }

        binding.todoClose.setOnClickListener {
            dismiss()

        }
    }

    interface OnDialogAddTaskButtonClickListener {
        fun saveTask(todoTask: String, todoEdit: TextInputEditText)

        fun updateTask(toDoData: ToDoData, todoEdit: TextInputEditText)

    }

}