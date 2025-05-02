package com.example.innostudent

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.innostudent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme before setContentView to prevent white flash
        setTheme(R.style.Theme_InnoStudent)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Force dark status bar icons
        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        // Set system bar colors
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        // Set background to prevent white screen
        window.setBackgroundDrawableResource(R.color.background_dark)

        // Initialize fragments
        if (savedInstanceState == null) {
            loadInitialFragment()
        }

        setupBottomNavigation()

        Log.d("MainActivity", "Activity created successfully")
    }

    private fun loadInitialFragment() {
        try {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment(), "HOME_TAG")
                .commitNow()
            Log.d("MainActivity", "HomeFragment loaded successfully")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error loading initial fragment", e)
            // Fallback to simple TextView if fragment fails
            binding.fragmentContainer.removeAllViews()
            val tv = android.widget.TextView(this).apply {
                text = "Welcome"
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }
            binding.fragmentContainer.addView(tv)
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_projects -> replaceFragment(ProjectsFragment())
                R.id.nav_funding -> replaceFragment(FundingFragment())
                R.id.nav_feedback -> replaceFragment(FeedbackFragment())
                R.id.nav_profile -> replaceFragment(ProfileFragment())
            }
            true
        }
        Log.d("MainActivity", "Bottom navigation setup complete")
    }

    private fun replaceFragment(fragment: Fragment) {
        try {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                .replace(R.id.fragmentContainer, fragment)
                .commit()
            Log.d("MainActivity", "Fragment replaced successfully")
        } catch (e: Exception) {
            Log.e("MainActivity", "Fragment replacement failed", e)
        }
    }
}