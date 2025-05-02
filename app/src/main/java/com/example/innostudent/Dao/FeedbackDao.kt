package com.example.innostudent.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.innostudent.models.Feedback

@Dao
interface FeedbackDao {

    @Insert
    suspend fun insert(feedback: Feedback) // This is the method you're calling in 'insertFeedback()'

    @Query("SELECT * FROM feedback WHERE projectId = :projectId")
    fun getFeedbacksForProject(projectId: Int): LiveData<List<Feedback>>
}
