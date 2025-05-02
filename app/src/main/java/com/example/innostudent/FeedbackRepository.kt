// FeedbackRepository.kt
package com.example.innostudent

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.innostudent.models.Feedback

class FeedbackRepository(context: Context) {
    private val feedbackDao = FeedbackDatabase.getDatabase(context).feedbackDao()

    // Insert feedback into the database
    suspend fun insert(feedback: Feedback) {
        feedbackDao.insert(feedback)
    }

    // Get all feedbacks for a project
    fun getAllFeedbacks(projectId: Int): LiveData<List<Feedback>> {
        return feedbackDao.getFeedbacksForProject(projectId)
    }
}
