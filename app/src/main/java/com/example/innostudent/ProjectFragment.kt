package com.example.innostudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innostudent.databinding.FragmentProjectBinding
import com.example.innostudent.models.Project


import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProjectsFragment : Fragment() {

    private var _binding: FragmentProjectBinding? = null
    private val binding get() = _binding!!

    private lateinit var projectAdapter: ProjectAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        binding.fabAddProject.setOnClickListener {
            showAddProjectDialog()
        }

        fetchProjects()

        return binding.root
    }

    private fun fetchProjects() {
        val db = AppDatabase.getDatabase(requireContext())

        GlobalScope.launch {
            val projects = db.projectDao().getAllProjects()
            activity?.runOnUiThread {
                projectAdapter = ProjectAdapter(projects)
                recyclerView.adapter = projectAdapter
            }
        }
    }

    private fun showAddProjectDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add New Project")

        val input = EditText(requireContext())
        input.hint = "Enter Project Name"
        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, _ ->
            val projectName = input.text.toString().trim()
            if (projectName.isNotEmpty()) {
                insertProject(projectName)
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun insertProject(projectName: String) {
        val db = AppDatabase.getDatabase(requireContext())

        val newProject = Project(
            id = System.currentTimeMillis().toString(),
            name = projectName,
            description = "Description for $projectName",
            category = "General",
            imageUrl = ""
        )

        GlobalScope.launch {
            db.projectDao().insertProject(newProject)

            activity?.runOnUiThread {
                NotificationHelper.showNotification(
                    requireContext(),
                    "New Project Added",
                    "Project \"$projectName\" added successfully!"
                )
                fetchProjects()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
