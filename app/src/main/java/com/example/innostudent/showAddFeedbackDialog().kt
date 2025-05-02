package com.example.innostudent

import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.innostudent.models.Feedback
import kotlinx.coroutines.launch

fun Fragment.showAddFeedbackDialog(
    projectId: Int,
    userId: Int,
    reviewerName: String,
    viewModel: FeedbackViewModel
) {
    // Creating an EditText inside an AlertDialog for feedback entry
    val input = EditText(requireContext()).apply {
        hint = "Enter your feedback"
    }

    // Setting up the AlertDialog
    val builder = AlertDialog.Builder(requireContext()).apply {
        setTitle("Add Feedback")
        setView(input)

        // Set the "Add" button click listener
        setPositiveButton("Add") { dialog, _ ->
            val feedbackText = input.text.toString().trim()

            // Check if feedback is not empty
            if (feedbackText.isNotEmpty()) {
                // Create a new Feedback object
                val feedback = Feedback(
                    projectId = projectId,
                    userId = userId,
                    name = reviewerName,
                    comment = feedbackText,
                    timestamp = System.currentTimeMillis()
                )

                // Insert the feedback into the database using coroutine
                lifecycleScope.launch {
                    viewModel.insertFeedback(feedback)
                }

                // Show a notification after feedback submission
                NotificationHelper.showNotification(
                    requireContext(),
                    "Feedback Submitted",
                    "Thank you for your feedback!"
                )
            }

            // Dismiss the dialog
            dialog.dismiss()
        }

        // Set the "Cancel" button click listener
        setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
    }

    // Show the AlertDialog
    builder.show()
}
