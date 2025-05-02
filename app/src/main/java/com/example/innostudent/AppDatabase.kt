package com.example.innostudent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.innostudent.Dao.FeedbackDao
import com.example.innostudent.Dao.FundingRequestDao
import com.example.innostudent.Dao.ProjectDao
import com.example.innostudent.models.Feedback
import com.example.innostudent.models.FundingRequest
import com.example.innostudent.models.Project

@Database(
    entities = [Feedback::class, Project::class, FundingRequest::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun feedbackDao(): FeedbackDao
    abstract fun projectDao(): ProjectDao
    abstract fun fundingRequestDao(): FundingRequestDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "innostudent-db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
