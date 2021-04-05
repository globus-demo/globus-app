package com.technopolis_education.globusapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainAuthorization : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorization_main)
    }

    override fun onResume() {
        super.onResume()
        val login = findViewById<TextView>(R.id.login)
        val password = findViewById<TextView>(R.id.password)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            if (!login.text.equals("") || !password.text.equals("")) {
                val toast = Toast.makeText(applicationContext, "Login or password field is empty", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }
}