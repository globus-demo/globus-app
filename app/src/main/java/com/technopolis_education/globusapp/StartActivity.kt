package com.technopolis_education.globusapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_page_main);

        val sharedPreferences = getSharedPreferences("AUTH", Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("Success", false)){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        } else {
            val intent = Intent(this, MainAuthorization::class.java)
            startActivity(intent);
        }
    }
}