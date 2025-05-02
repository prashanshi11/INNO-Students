package com.example.innostudent.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feedback")
data class Feedback(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val projectId: Int,
    val userId: Int,
    val name: String,
    val comment: String,
    val timestamp: Long
)
