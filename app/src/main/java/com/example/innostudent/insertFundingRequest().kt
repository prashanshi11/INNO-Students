package com.example.innostudent

import android.app.Activity
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.innostudent.models.FundingRequest

// Add a context parameter to the function
fun insertFundingRequest(
    context: Context,
    title: String,
    amount: Double,
    description: String,
    onComplete: (() -> Unit)? = null
) {
    val db = AppDatabase.getDatabase(context)

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

        (context as? Activity)?.runOnUiThread {
            NotificationHelper.showNotification(
                context,
                "Funding Request Submitted",
                "₹$amount requested for \"$title\""
            )
            onComplete?.invoke() // Use this to clear EditTexts from the calling class
        }
    }
}
