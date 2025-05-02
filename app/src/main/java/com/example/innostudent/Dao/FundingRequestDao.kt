package com.example.innostudent.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.innostudent.models.FundingRequest

@Dao
interface FundingRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFundingRequest(fundingRequest: FundingRequest)

    @Query("SELECT * FROM funding_requests WHERE projectId = :projectId")
    fun getFundingRequestsByProjectId(projectId: Int): LiveData<List<FundingRequest>>

    @Query("SELECT * FROM funding_requests")
    suspend fun getAllFundingRequests(): List<FundingRequest>

    @Delete
    suspend fun deleteFundingRequest(fundingRequest: FundingRequest)
}
