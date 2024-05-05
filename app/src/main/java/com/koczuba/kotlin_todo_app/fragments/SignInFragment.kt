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
import com.koczuba.kotlin_todo_app.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {


    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init(view)
        registerEvents()
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()

    }

    private fun registerEvents() {

        binding.textViewSignUp.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.signInButton.setOnClickListener {
            val email = binding.signInEmailET.text.toString().trim()
            val password = binding.signInPasswordET.text.toString().trim()

            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.signInProgressBar.visibility = View.VISIBLE
            binding.signInButton.visibility = View.GONE
            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Login Successfull", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_signInFragment_to_homeFragment)
            } else

                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            binding.signInProgressBar.visibility = View.GONE
            binding.signInButton.visibility = View.VISIBLE
        }
    }


}