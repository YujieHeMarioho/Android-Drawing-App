package com.example.finaldraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val textViewStatus = view.findViewById<TextView>(R.id.textViewStatus)
        val editTextEmail = view.findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = view.findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = view.findViewById<Button>(R.id.buttonLogin)
        val buttonSignUp = view.findViewById<Button>(R.id.buttonSignUp)
        val textViewData = view.findViewById<TextView>(R.id.textViewData)

        auth = FirebaseAuth.getInstance()
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            signInWithEmail(email, password, textViewStatus)
        }

        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            createAccount(email, password, textViewStatus)
        }

        return view
    }

    private fun createAccount(email: String, password: String, statusView: TextView) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    statusView.text = "Sign up successful, user created."
                } else {
                    statusView.text = "Sign up failed: ${task.exception?.message}"
                }
            }
    }

    private fun signInWithEmail(email: String, password: String, statusView: TextView) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    statusView.text = "Login successful, welcome!"
                } else {
                    statusView.text = "Login failed: ${task.exception?.message}"
                }
            }
    }
}
