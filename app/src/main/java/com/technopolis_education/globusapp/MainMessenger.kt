package com.technopolis_education.globusapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainMessenger : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messenger_main)
        title = "Messenger"

        val bottomView: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.feed -> {
                    startActivity(Intent(this, FeedActivity::class.java))
                    true
                }
                R.id.messages -> {
                    startActivity(Intent(this, MainMessenger::class.java))
                    true
                }
                R.id.globe -> {
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}