package com.example.innostudent

import android.content.Context
import com.example.innostudent.models.Feedback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun insertFeedback(context: Context, feedbackText: String, projectId: Int, userId: Int, reviewerName: String) {
    val db = AppDatabase.getDatabase(context) // Access your Room database

    val newFeedback = Feedback(
        id = 0, // Let Room auto-generate the ID
        projectId = projectId,
        userId = userId,
        name = reviewerName,
        comment = feedbackText,
        timestamp = System.currentTimeMillis()
    )

    // Use the correct DAO method (insert)
    GlobalScope.launch {
        db.feedbackDao().insert(newFeedback) // Correct method is 'insert'

        // If you need to update the UI after insertion, you can use runOnUiThread in the activity or fragment
        (context as? MainActivity)?.runOnUiThread {
            NotificationHelper.showNotification(
                context,
                "New Feedback Added",
                "Feedback added successfully!"
            )
            // Call any method to refresh the feedback list in the UI (if needed)
        }
    }
}
