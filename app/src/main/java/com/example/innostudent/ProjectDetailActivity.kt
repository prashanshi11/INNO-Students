package com.example.innostudent

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.innostudent.models.Project


class ProjectDetailActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvStudentName: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnUploadDoc: Button
    private lateinit var btnUploadSlides: Button
    private lateinit var btnSubmitFeedback: Button
    private lateinit var btnFundProject: Button

    private var selectedDocUri: Uri? = null
    private var selectedSlideUri: Uri? = null

    private val pickDocLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedDocUri = it
            Toast.makeText(this, "Document selected: ${it.lastPathSegment}", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickSlideLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedSlideUri = it
            Toast.makeText(this, "Presentation selected: ${it.lastPathSegment}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        val project = intent.getParcelableExtra<Project>("project")

        if (project == null) {
            Toast.makeText(this, "Project data not found!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Initialize views
        tvTitle = findViewById(R.id.tvDetailTitle)
        tvDescription = findViewById(R.id.tvDetailDescription)
        tvStudentName = findViewById(R.id.tvDetailStudentName)
        tvDate = findViewById(R.id.tvDetailDate)
        tvStatus = findViewById(R.id.tvDetailStatus)
        btnUploadDoc = findViewById(R.id.btnUploadDoc)
        btnUploadSlides = findViewById(R.id.btnUploadSlides)
        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback)
        btnFundProject = findViewById(R.id.btnFundProject)

        // Set data
        tvTitle.text = project.title
        tvDescription.text = project.description
        tvStudentName.text = "👤 ${project.name}"
        tvDate.text = "📅 ${project.date}"
        tvStatus.text = when (project.status.lowercase()) {
            "approved" -> "🟢 Approved"
            "rejected" -> "🔴 Rejected"
            else -> "🟡 Pending"
        }

        // Button handlers
        btnUploadDoc.setOnClickListener {
            pickDocLauncher.launch("application/pdf")
        }

        btnUploadSlides.setOnClickListener {
            pickSlideLauncher.launch("*/*") // Allow pptx, pdf, etc.
        }

        btnSubmitFeedback.setOnClickListener {
            Toast.makeText(this, "Feedback submitted!", Toast.LENGTH_SHORT).show()
        }

        btnFundProject.setOnClickListener {
            Toast.makeText(this, "Funding process started!", Toast.LENGTH_SHORT).show()
        }

        // Example role-based UI logic (optional)
        val userRole = "mentor" // ← This should come from Firebase or SharedPreferences
        if (userRole == "student") {
            btnSubmitFeedback.visibility = Button.GONE
            btnFundProject.visibility = Button.GONE
        } else if (userRole == "mentor") {
            btnUploadDoc.visibility = Button.GONE
            btnUploadSlides.visibility = Button.GONE
        }
    }
}
