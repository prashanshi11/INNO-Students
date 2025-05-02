package com.example.innostudent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innostudent.models.Project
import androidx.appcompat.app.AppCompatActivity

class ProjectListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var projectAdapter: ProjectAdapter
    private val projectList = arrayListOf<Project>()

    private lateinit var btnAddProject: Button

    // Register for project submission result
    private val submissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newProject = result.data?.getParcelableExtra<Project>("project")
            newProject?.let {
                projectList.add(0, it) // Add new project at the top
                projectAdapter.notifyItemInserted(0)
                recyclerView.scrollToPosition(0)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)

        val userRole = intent.getStringExtra("user_role") ?: "Student"

        recyclerView = findViewById(R.id.projectRecyclerView)
        btnAddProject = findViewById(R.id.btnAddProject)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add dummy project data
        projectList.addAll(
            listOf(
                Project(
                    id = "1",
                    title = "Smart Agriculture",
                    description = "Using AI & IoT to optimize crops.",
                    name = "John",
                    date = "2025-04-16",
                    status = "pending",
                    category = "Agriculture",
                    imageUrl = "",
                    ownerId = "user1"
                ),
                Project(
                    id = "2",
                    title = "Energy Saver",
                    description = "Create an AI-powered energy-saving system.",
                    name = "Sarah",
                    date = "2025-04-15",
                    status = "approved",
                    category = "Energy",
                    imageUrl = "",
                    ownerId = "user2"
                ),
                // Add other projects similarly...
            )
        )

        // Pass only the projectList to the adapter
        projectAdapter = ProjectAdapter(projectList)
        recyclerView.adapter = projectAdapter

        // Show Add button only if user is a Student
        if (userRole == "Student") {
            btnAddProject.visibility = View.VISIBLE
            btnAddProject.setOnClickListener {
                val intent = Intent(this, SubmitProjectActivity::class.java)
                submissionLauncher.launch(intent)
            }
        } else {
            btnAddProject.visibility = View.GONE
        }
    }
}
