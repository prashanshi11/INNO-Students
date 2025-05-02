package com.example.innostudent

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innostudent.models.FundingRequest
import com.google.firebase.firestore.FirebaseFirestore

class FundingApplicationActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etAmount: EditText
    private lateinit var etReason: EditText
    private lateinit var btnSubmit: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var fundingList: ArrayList<FundingRequest>
    private lateinit var adapter: FundingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funding_application)

        // Initialize views
        etTitle = findViewById(R.id.etFundingTitle)
        etAmount = findViewById(R.id.etFundingAmount)
        etReason = findViewById(R.id.etFundingReason)
        btnSubmit = findViewById(R.id.btnSubmitFunding)
        recyclerView = findViewById(R.id.fundingRecyclerView)

        val role = intent.getStringExtra("user_role") ?: "Student"

        // Role-based behavior
        if (role != "Student") {
            showFundingList()
        } else {
            btnSubmit.setOnClickListener {
                val title = etTitle.text.toString().trim()
                val amountStr = etAmount.text.toString().trim()
                val reason = etReason.text.toString().trim()

                if (title.isNotEmpty() && amountStr.isNotEmpty() && reason.isNotEmpty()) {
                    val amount = amountStr.toDoubleOrNull()
                    if (amount == null) {
                        Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    // Create FundingRequest object
                    val fundingRequest = FundingRequest(
                        projectId = 0,  // You might want to pass the actual project ID here
                        amountRequested = amount,
                        fundingStatus = "Pending",
                        requestDate = System.currentTimeMillis().toString()
                    )

                    // Submit funding request to Firestore
                    val db = FirebaseFirestore.getInstance()
                    db.collection("funding_requests")
                        .add(fundingRequest)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Application submitted!", Toast.LENGTH_SHORT).show()
                            // Clear input fields
                            etTitle.text.clear()
                            etAmount.text.clear()
                            etReason.text.clear()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to submit: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Show the list of funding requests for mentors/admins
    private fun showFundingList() {
        etTitle.visibility = View.GONE
        etAmount.visibility = View.GONE
        etReason.visibility = View.GONE
        btnSubmit.visibility = View.GONE

        recyclerView.visibility = View.VISIBLE

        fundingList = arrayListOf()
        adapter = FundingAdapter(this, fundingList)  // Pass context and list here
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Fetch funding requests from Firestore
        val db = FirebaseFirestore.getInstance()
        db.collection("funding_requests")
            .get()
            .addOnSuccessListener { result ->
                fundingList.clear()
                for (document in result) {
                    val fundingRequest = document.toObject(FundingRequest::class.java)
                    fundingList.add(fundingRequest)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load funding requests: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
