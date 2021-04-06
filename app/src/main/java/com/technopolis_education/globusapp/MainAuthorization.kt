package com.technopolis_education.globusapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainAuthorization : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorization_main)
        title = "Authorization"

        val login = findViewById<EditText>(R.id.login)
        val password = findViewById<EditText>(R.id.password)
        val button = findViewById<Button>(R.id.button)
        val register = findViewById<TextView>(R.id.register)

        button.setOnClickListener {
            if (login.text.toString().isEmpty()) {
                login.hint = "This field must be filled"
                login.setHintTextColor(Color.RED)
            }

            if (password.text.toString().isEmpty()) {
                password.hint = "This field must be filled"
                password.setHintTextColor(Color.RED)
            }

            if (!password.text.toString().isEmpty() && !login.text.toString().isEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        register.setOnClickListener {
            val intent = Intent(this, MainRegister::class.java)
            startActivity(intent)
        }
    }
}