package com.example.innostudent

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innostudent.models.Project
import com.example.innostudent.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectViewModel(private val context: Context) : ViewModel() {

    // Function to insert a project into the Room database
    fun insertProject(projectName: String) {
        // Initialize the database
        val db = AppDatabase.getDatabase(context)

        // Create a new Project instance
        val newProject = Project(
            id = System.currentTimeMillis().toString(),  // Generating unique ID
            name = projectName,
            description = "Description for $projectName",  // Example placeholder description
            category = "General",  // Example category
            imageUrl = "",  // Placeholder for image URL
            ownerId = "userId"  // Example ownerId (this should be dynamic based on the logged-in user)
        )

        // Perform the insert operation asynchronously
        viewModelScope.launch {
            // Inserting the project in the background thread
            withContext(Dispatchers.IO) {
                db.projectDao().insertProject(newProject)
            }

            // You can call a callback here or update UI after the insert operation is done
            showNotification("New Project Added", "Project \"$projectName\" added successfully!")
        }
    }

    private fun showNotification(title: String, message: String) {
        // You can implement this function to show a notification to the user
        // Example:
        NotificationHelper.showNotification(context, title, message)
    }
}
