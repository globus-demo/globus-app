package com.technopolis_education.globusapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.technopolis_education.globusapp.databinding.ActivityMainBinding
import com.technopolis_education.globusapp.ui.auth.AuthorizationActivity
import com.technopolis_education.globusapp.ui.start.StartActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val bottomView: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        val navController = findNavController(R.id.main_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        bottomView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                val auth = getSharedPreferences("AUTH", Context.MODE_PRIVATE)
                auth.edit().clear().apply()
                val intent = Intent(this, AuthorizationActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}