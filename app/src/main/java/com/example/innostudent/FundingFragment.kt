package com.example.innostudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.innostudent.databinding.FragmentFundingBinding
import com.example.innostudent.models.FundingRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FundingFragment : Fragment() {

    private var _binding: FragmentFundingBinding? = null
    private val binding get() = _binding!!

    private lateinit var fundingAdapter: FundingAdapter
    private var fundingList: List<FundingRequest> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFundingBinding.inflate(inflater, container, false)

        binding.recyclerViewFunding.layoutManager = LinearLayoutManager(context)
        fetchFundingRequests()

        binding.btnApplyFunding.setOnClickListener {
            val title = binding.etFundingTitle.text.toString().trim()
            val amountText = binding.etFundingAmount.text.toString().trim()
            val description = binding.etFundingDescription.text.toString().trim()

            if (title.isEmpty() || amountText.isEmpty() || description.isEmpty()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(context, "Enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            insertFundingRequest(title, amount, description)
        }

        return binding.root
    }

    fun fetchFundingRequests() {
        val db = AppDatabase.getDatabase(requireContext())
        GlobalScope.launch {
            val fundingList = db.fundingRequestDao().getAllFundingRequests()
            activity?.runOnUiThread {
                if (!::fundingAdapter.isInitialized) {
                    fundingAdapter = FundingAdapter(requireContext(), fundingList.toMutableList())
                    binding.recyclerViewFunding.adapter = fundingAdapter
                } else {
                    fundingAdapter.updateList(fundingList)
                }
            }
        }
    }


    private fun insertFundingRequest(title: String, amount: Double, description: String) {
        val db = AppDatabase.getDatabase(requireContext())

        val newFundingRequest = FundingRequest(
            projectId = 1,
            projectName = title,
            projectDescription = description,
            amountRequested = amount,
            fundingStatus = "Pending",
            requestDate = System.currentTimeMillis().toString()
        )

        GlobalScope.launch {
            db.fundingRequestDao().insertFundingRequest(newFundingRequest)

            activity?.runOnUiThread {
                NotificationHelper.showNotification(
                    requireContext(),
                    "Funding Request Submitted",
                    "₹$amount requested for '$title'"
                )
                binding.etFundingTitle.text.clear()
                binding.etFundingAmount.text.clear()
                binding.etFundingDescription.text.clear()
                fetchFundingRequests()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
