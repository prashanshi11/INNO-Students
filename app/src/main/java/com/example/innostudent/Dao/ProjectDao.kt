package com.example.innostudent.Dao

import androidx.room.*
import com.example.innostudent.models.Project

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: Project)

    @Query("SELECT * FROM projects")
    suspend fun getAllProjects(): List<Project>


    @Query("SELECT * FROM projects WHERE id = :projectId")
    suspend fun getProjectById(projectId: Int): Project?

    @Delete
    suspend fun deleteProject(project: Project)
}
