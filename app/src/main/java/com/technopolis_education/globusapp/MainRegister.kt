package com.technopolis_education.globusapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_main)
        title = "Registration"

        val nameAndSurname = findViewById<EditText>(R.id.name_surname_field)
        val email = findViewById<EditText>(R.id.e_mail_field)
        val password = findViewById<EditText>(R.id.password_field)
        val confirmPassword = findViewById<EditText>(R.id.confirm_password_field)

        val button = findViewById<Button>(R.id.confirm_register)

        button.setOnClickListener {
            if (nameAndSurname.text.toString().isEmpty()) {
                nameAndSurname.hint = "This field must be filled"
                nameAndSurname.setHintTextColor(Color.RED)
            }

            if (email.text.toString().isEmpty()) {
                email.hint = "This field must be filled"
                email.setHintTextColor(Color.RED)
            }

            if (password.text.toString().isEmpty()) {
                password.hint = "This field must be filled"
                password.setHintTextColor(Color.RED)
            }

            if (confirmPassword.text.toString().isEmpty()) {
                confirmPassword.hint = "This field must be filled"
                confirmPassword.setHintTextColor(Color.RED)
            }

            if (!confirmPassword.text.toString().equals(password.text.toString())) {
                val toast =
                    Toast.makeText(applicationContext, "Password mismatch", Toast.LENGTH_SHORT)
                toast.show()
            }

            if (!nameAndSurname.text.toString().isEmpty() && !email.text.toString()
                    .isEmpty() && !password.text.toString()
                    .isEmpty() && !confirmPassword.text.toString()
                    .isEmpty() && confirmPassword.text.toString().equals(password.text.toString())
            ) {
                val intent = Intent(this, FeedActivity::class.java)
                startActivity(intent)
            }
        }
    }
}