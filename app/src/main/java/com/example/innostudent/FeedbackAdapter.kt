package com.example.innostudent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.innostudent.databinding.ItemFeedbackBinding
import com.example.innostudent.models.Feedback

class FeedbackAdapter(private val feedbackList: List<Feedback>) :
    RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {

    inner class FeedbackViewHolder(private val binding: ItemFeedbackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(feedback: Feedback) {
            binding.feedback = feedback
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val binding = ItemFeedbackBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FeedbackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        holder.bind(feedbackList[position])
    }

    override fun getItemCount(): Int = feedbackList.size
}
