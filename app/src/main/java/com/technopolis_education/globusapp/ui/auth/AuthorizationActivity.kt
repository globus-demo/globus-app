package com.technopolis_education.globusapp.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.technopolis_education.globusapp.MainActivity
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.logic.check.InternetConnectionCheck
import com.technopolis_education.globusapp.model.UserAuthRequest
import com.technopolis_education.globusapp.model.UserToken
import com.technopolis_education.globusapp.ui.registration.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizationActivity : AppCompatActivity() {

    private val webClient = WebClient().getApi()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorization_main)
        supportActionBar?.hide()

        val login = findViewById<TextInputEditText>(R.id.et_login)
        val tilLogin = findViewById<TextInputLayout>(R.id.til_login)
        val password = findViewById<TextInputEditText>(R.id.et_password)
        val tilPassword = findViewById<TextInputLayout>(R.id.til_password)
        val button = findViewById<Button>(R.id.button)
        val register = findViewById<TextView>(R.id.register)

        login.addTextChangedListener {
            if (tilLogin.error != null) {
                tilLogin.isErrorEnabled = false
                tilLogin.error = null
            }
        }
        password.addTextChangedListener {
            if (tilPassword.error != null) {
                tilPassword.isErrorEnabled = false
                tilPassword.error = null
            }
        }
        button.setOnClickListener {
            if (!InternetConnectionCheck().isOnline(this)) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.error_no_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (login.text.toString().isEmpty()) {
                    tilLogin.isErrorEnabled = true
                    tilLogin.error = getString(R.string.hint)
                }

                if (password.text.toString().isEmpty()) {
                    tilPassword.isErrorEnabled = true
                    tilPassword.error = getString(R.string.hint)
                }

                if (password.text.toString().isNotEmpty() && login.text.toString().isNotEmpty()) {

                    val userAuth = UserAuthRequest(
                        login.text.toString(),
                        password.text.toString()
                    )

                    val callAuth = webClient.auth(userAuth)
                    callAuth.enqueue(object : Callback<UserToken> {
                        override fun onResponse(
                            call: Call<UserToken>,
                            response: Response<UserToken>
                        ) {
                            if (response.body()?.status == true) {
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.auth_success),
                                    Toast.LENGTH_SHORT
                                ).show()
                                startFragment(response.body())
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.wrong_email_or_password),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<UserToken>, t: Throwable) {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.no_internet_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
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