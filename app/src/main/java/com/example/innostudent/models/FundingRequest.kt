package com.example.innostudent.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "funding_requests")
data class FundingRequest(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val projectId: Int = 0,
    val projectName: String = "",
    val projectDescription: String = "",
    val amountRequested: Double = 0.0,
    val fundingStatus: String = "Pending",
    val requestDate: String = ""
)
