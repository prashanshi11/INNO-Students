// FeedbackViewModel.kt
package com.example.innostudent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.innostudent.models.Feedback
import kotlinx.coroutines.launch

class FeedbackViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FeedbackRepository = FeedbackRepository(application)

    // Insert feedback into the database
    fun insertFeedback(feedback: Feedback) {
        viewModelScope.launch {
            repository.insert(feedback)
        }
    }

    // Get all feedbacks for a specific project
    fun getAllFeedbacks(projectId: Int): LiveData<List<Feedback>> {
        return repository.getAllFeedbacks(projectId)
    }
}
