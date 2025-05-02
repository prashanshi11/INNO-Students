package com.example.innostudent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.innostudent.models.Project
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class SubmitProjectActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnUploadDoc: Button
    private lateinit var btnSubmit: Button
    private lateinit var profileButton: Button
    private var documentUri: Uri? = null

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_submission)

        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        btnUploadDoc = findViewById(R.id.btnUploadDoc)
        btnSubmit = findViewById(R.id.btnSubmit)
        profileButton = findViewById(R.id.profileButton)

        profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnUploadDoc.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(intent, 100)
        }

        btnSubmit.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            uploadProject(title, description)
        }
    }

    private fun uploadProject(title: String, description: String) {
        val user = auth.currentUser ?: return
        val studentName = user.email ?: "Unknown"
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val id = UUID.randomUUID().toString()

        if (documentUri != null) {
            val ref = storage.reference.child("documents/$id")
            ref.putFile(documentUri!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        saveToFirestore(
                            Project(
                                id = id,
                                title = title,
                                description = description,
                                name = studentName,
                                date = date,
                                status = "pending"
                            )
                        )
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
                }
        } else {
            saveToFirestore(
                Project(
                    id = id,
                    title = title,
                    description = description,
                    name = studentName,
                    date = date,
                    status = "pending"
                )
            )
        }
    }

    private fun saveToFirestore(project: Project) {
        firestore.collection("projects")
            .document(project.id)
            .set(project)
            .addOnSuccessListener {
                Toast.makeText(this, "Project submitted successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save project", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            documentUri = data?.data
            Toast.makeText(this, "Document Selected", Toast.LENGTH_SHORT).show()
        }
    }
}
