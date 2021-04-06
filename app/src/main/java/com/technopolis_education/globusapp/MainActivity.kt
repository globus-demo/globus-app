package com.technopolis_education.globusapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomView: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        val navController = findNavController(R.id.main_fragment)
        bottomView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.feed -> {
                    navController.navigate(R.id.action_main_fragment_to_feed_fragment)
                    true
                }
                R.id.messages -> {
                    navController.navigate(R.id.action_feed_fragment_to_message_fragment)
                    true
                }
                R.id.globe -> {
                    navController.navigate(R.id.action_feed_fragment_to_message_fragment)
                    true
                }
                R.id.profile -> {
                    navController.navigate(R.id.action_feed_fragment_to_profile_fragment)
                    true
                }
                else -> false
            }
        }
    }
}