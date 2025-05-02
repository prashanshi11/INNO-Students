package com.example.innostudent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.innostudent.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener { loginUser() }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        binding.progressBar.visibility = View.VISIBLE
        Log.d("LoginActivity", "Attempting login with email: $email")

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            binding.progressBar.visibility = View.GONE
            if (task.isSuccessful) {
                Log.d("LoginActivity", "Login successful, navigating to MainActivity")
                navigateToMain()
            } else {
                val errorMsg = task.exception?.message ?: "Unknown error"
                Log.e("LoginActivity", "Login failed: $errorMsg")
                Toast.makeText(this, "Login failed: $errorMsg", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun navigateToMain() {
        try {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
            Log.d("LoginActivity", "MainActivity navigation complete")
        } catch (e: Exception) {
            Log.e("LoginActivity", "Failed to navigate to MainActivity", e)
            Toast.makeText(this, "Error opening app", Toast.LENGTH_SHORT).show()
        }
    }
}