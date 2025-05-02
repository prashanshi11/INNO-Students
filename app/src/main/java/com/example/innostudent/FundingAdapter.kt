package com.example.innostudent

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.innostudent.databinding.ItemFundingBinding
import com.example.innostudent.models.FundingRequest

class FundingAdapter(
    private val context: Context,
    private val fundingRequests: MutableList<FundingRequest>
) : RecyclerView.Adapter<FundingAdapter.FundingViewHolder>() {

    // Update the current list with new data
    fun updateList(newList: List<FundingRequest>) {
        fundingRequests.clear()
        fundingRequests.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundingViewHolder {
        val binding = ItemFundingBinding.inflate(LayoutInflater.from(context), parent, false)
        return FundingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FundingViewHolder, position: Int) {
        val fundingRequest = fundingRequests[position]
        holder.bind(fundingRequest)
    }

    override fun getItemCount(): Int = fundingRequests.size

    inner class FundingViewHolder(private val binding: ItemFundingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fundingRequest: FundingRequest) {
            binding.tvFundingProjectName.text = fundingRequest.projectName
            binding.tvFundingAmount.text = "Amount: ₹${fundingRequest.amountRequested}"
            binding.tvFundingStatus.text = "Status: ${fundingRequest.fundingStatus}"

            // Set status colors based on the funding request status
            val statusColor = when (fundingRequest.fundingStatus) {
                "Approved" -> R.color.green
                "Pending" -> R.color.orange
                else -> R.color.red
            }
            binding.tvFundingStatus.setTextColor(context.getColor(statusColor))
        }
    }
}
