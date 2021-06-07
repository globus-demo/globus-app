package com.technopolis_education.globusapp.ui.error

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.ui.auth.AuthorizationActivity


class ErrorActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

        val retryConnection = findViewById<Button>(R.id.retry_connection)

        val lastActivity = this.getSharedPreferences("LAST", Context.MODE_PRIVATE)
        var activity = ""

        if (lastActivity?.contains("LastActivity") == true){
            activity = lastActivity.getString("LastActivity", "").toString()
        }

        retryConnection.setOnClickListener {

            if (isOnline(this)) {
                Log.i("test", activity)
                val intent = Intent(this, activity.trim()::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Check your Internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context?): Boolean =
        (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }
}