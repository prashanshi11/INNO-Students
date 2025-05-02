package com.yourpackage.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _signupResult = MutableLiveData<Boolean>()
    val signupResult: LiveData<Boolean> = _signupResult

    private val _logoutResult = MutableLiveData<Boolean>()
    val logoutResult: LiveData<Boolean> = _logoutResult

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _loginResult.value = task.isSuccessful
                if (!task.isSuccessful) {
                    Toast.makeText(getApplication(), task.exception?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signupUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _signupResult.value = task.isSuccessful
                if (!task.isSuccessful) {
                    Toast.makeText(getApplication(), task.exception?.message ?: "Signup failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun logoutUser() {
        auth.signOut()
        _logoutResult.value = true
    }

    fun getCurrentUser() = auth.currentUser
}

