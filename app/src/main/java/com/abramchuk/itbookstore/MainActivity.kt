package com.abramchuk.itbookstore

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNav()
    }

    private fun setupNav() {
        val navController = findNavController(R.id.nav_host_fragment)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        findViewById<BottomNavigationView>(R.id.nav_view)
                .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> showBottomNav(navView)
                R.id.navigation_dashboard -> showBottomNav(navView)
                R.id.navigation_notifications -> showBottomNav(navView)
                R.id.navigation_bookSearch -> showBottomNav(navView)
                R.id.bookInfoFragment -> showBottomNav(navView)
                else -> hideBottomNav(navView)
            }
        }
    }

    private fun showBottomNav(navView: BottomNavigationView) {
        navView.visibility = View.VISIBLE
    }

    private fun hideBottomNav(navView: BottomNavigationView) {
        navView.visibility = View.GONE
    }
}