package com.koczuba.kotlin_todo_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.koczuba.kotlin_todo_app.R
import com.koczuba.kotlin_todo_app.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {


    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var binding: FragmentSignUpBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init(view)
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()

    }

    private fun registerEvents() {

        binding.textViewSignIn.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.signUpButton.setOnClickListener {
            val email = binding.signUpEmailET.text.toString().trim()
            val password = binding.signUpPasswordET.text.toString().trim()
            val rePassword = binding.signUpRePasswordET.text.toString().trim()

            if (email.isEmpty() && password.isEmpty() && rePassword.isEmpty()) {
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != rePassword) {
                Toast.makeText(context, "Password is not same", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            registerUser(email, password)
        }
    }

    private fun registerUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Register Successfull", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_signUpFragment_to_homeFragment)
            } else
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()

        }
    }


}