package com.technopolis_education.globusapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomView: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        val navController = findNavController(R.id.main_fragment)
        bottomView.setupWithNavController(navController)
    }
}