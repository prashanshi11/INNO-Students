package com.example.innostudent

import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.innostudent.models.Project
import com.example.innostudent.util.showToast

// This function is called from the Fragment where the user can add a new project
fun Fragment.showAddProjectDialog() {
    // Create an AlertDialog builder to show the input dialog
    val builder = AlertDialog.Builder(requireContext())
    builder.setTitle("Add New Project")

    // Create an EditText for the project name input
    val input = EditText(requireContext())
    input.hint = "Enter Project Name"
    builder.setView(input)

    // Set the positive button to add the project
    builder.setPositiveButton("Add") { dialog, _ ->
        val projectName = input.text.toString().trim()

        // Check if the input is not empty
        if (projectName.isNotEmpty()) {
            // If it's valid, call the method to insert the project
            insertProject(projectName)
        } else {
            // Show a toast message if input is empty
            Toast.makeText(requireContext(), "Project name cannot be empty", Toast.LENGTH_SHORT).show()
        }

        dialog.dismiss()  // Close the dialog after the action is done
    }

    // Set the negative button to cancel the dialog
    builder.setNegativeButton("Cancel") { dialog, _ ->
        dialog.cancel()
    }

    // Show the dialog
    builder.show()
}

// This function inserts a project into the Room database and handles background operations
fun Fragment.insertProject(projectName: String) {
    // Get the ViewModel (ensure you have properly set up the ViewModel for your fragment)
    val projectViewModel = ProjectViewModel(requireContext())

    // Insert the project through the ViewModel
    projectViewModel.insertProject(projectName)

    // Optionally, show a notification or update the UI after insertion
    showToast(requireContext(), "Project \"$projectName\" added successfully!")
}
