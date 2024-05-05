package com.koczuba.kotlin_todo_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.koczuba.kotlin_todo_app.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), AddToDoDialogFragment.OnDialogAddTaskButtonClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var authId: String
    private var addTaskDialogFragment: AddToDoDialogFragment? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init(view)
        binding.addTaskButton.setOnClickListener {
            registerEvents()
        }


    }

    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        authId = auth.currentUser!!.uid
        database = Firebase.database.reference.child("Tasks").child(authId)

    }

    private fun registerEvents() {
        addTaskDialogFragment = AddToDoDialogFragment()
        addTaskDialogFragment!!.setListener(this)
        addTaskDialogFragment!!.show(
            childFragmentManager,
            "AddToDoDialogFragment"
        )
    }

    override fun saveTask(todoTask: String, todoEdit: TextInputEditText) {
        database
            .push().setValue(todoTask)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Task Added Successfully", Toast.LENGTH_SHORT).show()
                    todoEdit.text = null

                } else {
                    Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        addTaskDialogFragment!!.dismiss()
    }

}