package com.technopolis_education.globusapp.ui.start

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.technopolis_education.globusapp.MainActivity
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.ui.auth.AuthorizationActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_page_main)

        val sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("Success", false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
        }
    }
}