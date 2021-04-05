package com.technopolis_education.globusapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomView:BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.feed -> {
                    startActivity(Intent(this, FeedActivity::class.java))
                    true
                }
                R.id.messages -> {
                    true
                }
                R.id.globe -> {
                    true
                }
                R.id.profile -> {
                    true
                }
                else -> false
            }
        }
    }
}