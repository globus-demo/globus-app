package com.technopolis_education.globusapp.ui.auth

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.technopolis_education.globusapp.MainActivity
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.model.UserAuthRequest
import com.technopolis_education.globusapp.model.UserToken
import com.technopolis_education.globusapp.ui.registration.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizationActivity : AppCompatActivity() {

    private val webClient = WebClient().getApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorization_main)
        title = getString(R.string.authorization)

        val login = findViewById<EditText>(R.id.login)
        val password = findViewById<EditText>(R.id.password)
        val button = findViewById<Button>(R.id.button)
        val register = findViewById<TextView>(R.id.register)

        button.setOnClickListener {
            if (login.text.toString().isEmpty()) {
                login.hint = getString(R.string.hint)
                login.setHintTextColor(Color.RED)
            }

            if (password.text.toString().isEmpty()) {
                password.hint = getString(R.string.hint)
                password.setHintTextColor(Color.RED)
            }

            if (password.text.toString().isNotEmpty() && login.text.toString().isNotEmpty()) {

                val userAuth = UserAuthRequest(
                    login.text.toString(),
                    password.text.toString()
                )

                val callAuth = webClient.auth(userAuth)
                callAuth.enqueue(object : Callback<UserToken> {
                    override fun onResponse(call: Call<UserToken>, response: Response<UserToken>) {
                        if (response.body()?.status == true) {
                            Toast.makeText(
                                applicationContext,
                                "Successful authorization",
                                Toast.LENGTH_SHORT
                            ).show()
                            startFragment(response.body())
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Incorrect email or password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<UserToken>, t: Throwable) {
                        Log.i("test", "error $t")
                    }
                })
            }
        }

        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startFragment(userInfo: UserToken?) {
        val auth = getSharedPreferences("AUTH", Context.MODE_PRIVATE)
        val userToken = getSharedPreferences("USER TOKEN", Context.MODE_PRIVATE)
        val userId = getSharedPreferences("USER ID", Context.MODE_PRIVATE)
        userId.edit().putString("UserId", userInfo?.objectToResponse?.id).apply()
        auth.edit().putBoolean("Success", true).apply()
        userToken.edit().putString("UserToken", userInfo?.objectToResponse?.token).apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}