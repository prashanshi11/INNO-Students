package com.example.innostudent.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "projects")
data class Project(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val description: String = "",
    val name: String = "",
    val date: String = "",
    val status: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val ownerId: String = ""
) : Parcelable
