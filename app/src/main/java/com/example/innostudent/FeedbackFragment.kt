package com.example.innostudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.innostudent.databinding.FragmentFeedbackBinding
import com.example.innostudent.models.Feedback
import kotlinx.coroutines.launch

class FeedbackFragment : Fragment() {

    private var _binding: FragmentFeedbackBinding? = null
    private val binding get() = _binding!!

    private lateinit var feedbackAdapter: FeedbackAdapter
    private val viewModel: FeedbackViewModel by viewModels()

    private val projectId = 1  // Replace with actual project ID dynamically
    private val userId = 101   // Replace with logged-in user ID
    private val reviewerName = "John Doe"  // Replace with actual user name

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedbackBinding.inflate(inflater, container, false)

        // Set up RecyclerView
        binding.feedbackRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        feedbackAdapter = FeedbackAdapter(emptyList())
        binding.feedbackRecyclerView.adapter = feedbackAdapter

        observeFeedbacks()

        // Handle Submit Feedback button click
        binding.btnSubmitFeedback.setOnClickListener {
            val feedbackText = binding.etFeedback.text.toString().trim()
            if (feedbackText.isNotEmpty()) {
                val feedback = Feedback(
                    projectId = projectId,
                    userId = userId,
                    name = reviewerName,
                    comment = feedbackText,
                    timestamp = System.currentTimeMillis()
                )

                lifecycleScope.launch {
                    viewModel.insertFeedback(feedback)
                }

                // Clear input field and show confirmation
                binding.etFeedback.text.clear()
                NotificationHelper.showNotification(
                    requireContext(),
                    "Feedback Submitted",
                    "Thank you for your feedback!"
                )
            } else {
                Toast.makeText(requireContext(), "Feedback cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun observeFeedbacks() {
        viewModel.getAllFeedbacks(projectId).observe(viewLifecycleOwner) { feedbackList ->
            feedbackAdapter = FeedbackAdapter(feedbackList)
            binding.feedbackRecyclerView.adapter = feedbackAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
