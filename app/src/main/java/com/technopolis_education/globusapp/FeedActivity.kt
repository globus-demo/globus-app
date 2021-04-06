package com.technopolis_education.globusapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.technopolis_education.globusapp.db.posts.PostDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class FeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_activity)
        setTitle(R.string.feed_page)

        // work with db
        val db = Room.databaseBuilder(
            applicationContext,
            PostDatabase::class.java, "post"
        ).build()
        val postDao = db.postDao()
        GlobalScope.async {

        }

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