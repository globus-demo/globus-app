package com.technopolis_education.globusapp.ui.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.technopolis_education.globusapp.MainActivity
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.model.UserRegistrationRequest
import com.technopolis_education.globusapp.model.UserToken
import com.technopolis_education.globusapp.type.DialogType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private val webClient = WebClient().getApi()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val nameAndSurname = findViewById<TextInputEditText>(R.id.name_surname_field)
        val email = findViewById<TextInputEditText>(R.id.e_mail_field)
        val password = findViewById<TextInputEditText>(R.id.password_field)
        val confirmPassword = findViewById<TextInputEditText>(R.id.confirm_password_field)

        val tilLogin = findViewById<TextInputLayout>(R.id.til_login)
        val tilPassword = findViewById<TextInputLayout>(R.id.til_password)
        val tilName = findViewById<TextInputLayout>(R.id.til_name)
        val tilConfirmPassword = findViewById<TextInputLayout>(R.id.til_confirm_password)

        val confirm = findViewById<Button>(R.id.confirm_register)

        nameAndSurname.addTextChangedListener {
            tilName?.let {
                it.isErrorEnabled = false
                it.error = null
            }
        }
        password.addTextChangedListener {
            tilPassword?.let {
                it.isErrorEnabled = false
                it.error = null
            }
        }
        email.addTextChangedListener {
            tilLogin?.let {
                it.isErrorEnabled = false
                it.error = null
            }
        }
        confirmPassword.addTextChangedListener {
            tilConfirmPassword?.let {
                it.isErrorEnabled = false
                it.error = null
            }
        }

        confirm.setOnClickListener {
            if (nameAndSurname.text.toString().isEmpty()) {
                tilName?.let {
                    it.isErrorEnabled = true
                    it.error = getString(R.string.hint)
                }
            }

            if (email.text.toString().isEmpty()) {
                tilLogin?.let {
                    it.isErrorEnabled = true
                    it.error = getString(R.string.hint)
                }
            }

            if (password.text.toString().isEmpty()) {
                tilPassword?.let {
                    it.isErrorEnabled = true
                    it.error = getString(R.string.hint)
                }
            }

            if (confirmPassword.text.toString().isEmpty()) {
                tilConfirmPassword?.let {
                    it.isErrorEnabled = true
                    it.error = getString(R.string.hint)
                }
            }

            if (confirmPassword.text.toString() != password.text.toString()) {
                val toast =
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.password_missmatch),
                        Toast.LENGTH_SHORT
                    )
                toast.show()
            }

            if (nameAndSurname.text.toString().isNotEmpty()
                && email.text.toString().isNotEmpty()
                && password.text.toString().isNotEmpty()
                && confirmPassword.text.toString().isNotEmpty()
                && confirmPassword.text.toString() == password.text.toString()
//                && checkFormat(password.text.toString(), DialogType.PASSWORD)
                && checkFormat(email.text.toString(), DialogType.EMAIL)
                && checkFormat(nameAndSurname.text.toString(), DialogType.USERNAME)
            ) {
                val userName = nameAndSurname.text.toString().split(" ")[0]
                val userSurname = nameAndSurname.text.toString().split(" ")[1]
                val userReg = UserRegistrationRequest(
                    userName,
                    userSurname,
                    email.text.toString(),
                    password.text.toString()
                )

                val callReg = webClient.reg(userReg)
                callReg.enqueue(object : Callback<UserToken> {
                    override fun onResponse(call: Call<UserToken>, response: Response<UserToken>) {
                        startFragment(response.body())
                    }

                    override fun onFailure(call: Call<UserToken>, t: Throwable) {
                        Log.i("test", "error $t")
                    }
                })
            } else if (nameAndSurname.text.toString().isNotEmpty()
                && email.text.toString().isNotEmpty()
                && password.text.toString().isNotEmpty()
                && confirmPassword.text.toString().isNotEmpty()
            ) {
                if (!checkFormat(email.text.toString(), DialogType.EMAIL)) {
                    val toast =
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.invalid_email),
                            Toast.LENGTH_SHORT
                        )
                    toast.show()
                } else if (!checkFormat(nameAndSurname.text.toString(), DialogType.USERNAME)) {
                    val toast =
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.invalid_username),
                            Toast.LENGTH_SHORT
                        )
                    toast.show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startFragment(userResponse: UserToken?) {
        val auth = getSharedPreferences("AUTH", Context.MODE_PRIVATE)
        val userToken = getSharedPreferences("USER TOKEN", Context.MODE_PRIVATE)
        val userId = getSharedPreferences("USER ID", Context.MODE_PRIVATE)
        auth.edit().putBoolean("Success", true).apply()
        userToken.edit().putString("UserToken", userResponse?.objectToResponse?.token).apply()
        userId.edit().putString("UserId", userResponse?.objectToResponse?.id).apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun checkFormat(edit: String, typeOfDialog: DialogType): Boolean {
        return when (typeOfDialog) {
            DialogType.USERNAME -> edit.matches("[a-zA-Z][a-zA-Z_0-9\\-]+ [a-zA-Z][a-zA-Z_0-9\\-]+".toRegex())
            DialogType.EMAIL -> edit.matches("[a-zA-Z_0-9\\-]+.[a-zA-Z][a-zA-Z_0-9\\-]+@[a-z]{2,7}\\.[a-zA-Z_0-9\\-]+".toRegex()) && edit.length < 255
            DialogType.PASSWORD -> edit.matches("[a-zA-Z0-9!@#$%&]+".toRegex())
        }
    }
}