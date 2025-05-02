package com.example.innostudent

import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.innostudent.models.FundingRequest

// This function is called from the FundingFragment
fun Fragment.showAddFundingDialog() {
    // Create the AlertDialog builder
    val builder = AlertDialog.Builder(requireContext())
    builder.setTitle("Request Funding")

    // Create an EditText for user input (amount requested)
    val input = EditText(requireContext())
    input.hint = "Enter Amount Requested"
    input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
    builder.setView(input)

    // Set the positive button ("Request") to insert a new funding request
    builder.setPositiveButton("Request") { dialog, _ ->
        val amountText = input.text.toString().trim()

        if (amountText.isNotEmpty()) {
            val amount = amountText.toDouble()
            insertFundingRequest(amount)
        }

        // Dismiss the dialog once the action is completed
        dialog.dismiss()
    }

    // Set the negative button ("Cancel") to cancel the action
    builder.setNegativeButton("Cancel") { dialog, _ ->
        dialog.cancel()
    }

    // Show the dialog
    builder.show()
}

// This function inserts the funding request and refreshes the list
fun Fragment.insertFundingRequest(amount: Double) {
    // Get the database reference
    val db = AppDatabase.getDatabase(requireContext())

    // Create a new FundingRequest object
    val newFundingRequest = FundingRequest(
        projectId = 1,  // assuming projectId is 1, you should replace with the actual ID
        projectName = "Sample Project",  // replace with dynamic project name
        amountRequested = amount,
        fundingStatus = "Pending",
        requestDate = System.currentTimeMillis().toString()
    )

    // Insert the funding request into the database asynchronously
    GlobalScope.launch {
        // Insert the funding request into the database
        db.fundingRequestDao().insertFundingRequest(newFundingRequest)

        // Update UI after insertion on the main thread
        activity?.runOnUiThread {
            NotificationHelper.showNotification(
                requireContext(),
                "New Funding Request",
                "Funding request of ₹$amount submitted!"
            )

            // Refresh the funding requests list after submitting the new request
            (activity as? FundingFragment)?.fetchFundingRequests()
        }
    }
}
