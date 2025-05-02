package com.example.innostudent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var roleTextView: TextView
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Bind UI elements
        nameTextView = findViewById(R.id.nameTextView)
        emailTextView = findViewById(R.id.emailTextView)
        roleTextView = findViewById(R.id.roleTextView)
        logoutButton = findViewById(R.id.logoutButton)

        // Get current user
        val user = auth.currentUser
        if (user != null) {
            // Display user's email
            emailTextView.text = "Email: ${user.email}"

            // Fetch user details from Firestore
            db.collection("users").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Set name and role from Firestore data
                        nameTextView.text = "Name: ${document.getString("name") ?: "Unknown"}"
                        roleTextView.text = "Role: ${document.getString("role") ?: "Unknown"}"
                    }
                }
                .addOnFailureListener { e ->
                    // Handle any failure in fetching data
                    nameTextView.text = "Error: ${e.message}"
                    roleTextView.text = "Error: ${e.message}"
                }
        }

        // Set up logout button
        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
