// FeedbackDatabase.kt
package com.example.innostudent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.innostudent.Dao.FeedbackDao
import com.example.innostudent.models.Feedback

@Database(entities = [Feedback::class], version = 1)
abstract class FeedbackDatabase : RoomDatabase() {
    abstract fun feedbackDao(): FeedbackDao

    companion object {
        private var INSTANCE: FeedbackDatabase? = null

        fun getDatabase(context: Context): FeedbackDatabase {
            if (INSTANCE == null) {
                synchronized(FeedbackDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FeedbackDatabase::class.java,
                        "feedback_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}
