package com.example.innostudent

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innostudent.models.Feedback

class FeedbackActivity : AppCompatActivity() {

    private lateinit var feedbackRecyclerView: RecyclerView
    private lateinit var etFeedback: EditText
    private lateinit var btnSubmitFeedback: Button

    private val feedbackList = arrayListOf<Feedback>()
    private lateinit var feedbackAdapter: FeedbackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        feedbackRecyclerView = findViewById(R.id.feedbackRecyclerView)
        etFeedback = findViewById(R.id.etFeedback)
        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback)

        feedbackRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Adapter
        feedbackAdapter = FeedbackAdapter(feedbackList)
        feedbackRecyclerView.adapter = feedbackAdapter

        val projectId = intent.getStringExtra("projectId")?.toIntOrNull() ?: 0

        // 🔁 Replace this with actual Firebase or session-based user info
        val userId = 1
        val userName = "Mentor John" // Temporary hardcoded value

        btnSubmitFeedback.setOnClickListener {
            val feedbackText = etFeedback.text.toString().trim()
            if (feedbackText.isNotEmpty()) {
                val feedback = Feedback(
                    projectId = projectId,
                    userId = userId,
                    name = userName, // ✅ Added name
                    comment = feedbackText,
                    timestamp = System.currentTimeMillis()
                )
                feedbackList.add(0, feedback)
                feedbackAdapter.notifyItemInserted(0)
                feedbackRecyclerView.scrollToPosition(0)
                etFeedback.text.clear()
            } else {
                Toast.makeText(this, "Please enter feedback first!", Toast.LENGTH_SHORT).show()
            }
        }

        // TODO: Load feedback from Room or Firebase here
    }
}
