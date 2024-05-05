package com.koczuba.kotlin_todo_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.koczuba.kotlin_todo_app.databinding.FragmentAddToDoDialogBinding

class AddToDoDialogFragment : DialogFragment() {


    private lateinit var binding: FragmentAddToDoDialogBinding
    private var listener: OnDialogAddTaskButtonClickListener? = null

    fun setListener(listener: OnDialogAddTaskButtonClickListener) {
        this.listener = listener
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

        registerEvents()

    }

    private fun registerEvents() {

        binding.signInButtonCard.setOnClickListener {
            val todoTask = binding.todoTaskEt.text.toString()
            if (todoTask.isEmpty()) {
                return@setOnClickListener
            }
            listener?.saveTask(todoTask, binding.todoTaskEt)

        }

        binding.todoClose.setOnClickListener {
            dismiss()

        }
    }

    interface OnDialogAddTaskButtonClickListener {
        fun saveTask(todoTask: String, todoEdit: TextInputEditText)
    }

}