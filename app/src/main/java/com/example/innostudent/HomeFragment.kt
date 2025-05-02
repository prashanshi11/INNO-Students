package com.example.innostudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.innostudent.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        initializeUI()
    }

    private fun setupClickListeners() {
        binding.btnSubmitIdea.setOnClickListener {
            // Handle idea submission
            navigateToSubmitIdea()
        }

        binding.btnFindCollaborators.setOnClickListener {
            // Handle collaborator search
            navigateToCollaborators()
        }
    }

    private fun initializeUI() {
        // Initialize any UI components here
        // Example: binding.textWelcome.text = "Welcome, User!"
    }

    private fun navigateToSubmitIdea() {
        // Example navigation:
        // val action = HomeFragmentDirections.actionHomeToSubmitIdea()
        // findNavController().navigate(action)
    }

    private fun navigateToCollaborators() {
        // Example navigation:
        // val action = HomeFragmentDirections.actionHomeToCollaborators()
        // findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}