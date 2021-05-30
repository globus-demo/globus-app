package com.technopolis_education.globusapp.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.logic.adapter.profile.ProfileFriendsAdapter
import com.technopolis_education.globusapp.model.RegResponse
import com.technopolis_education.globusapp.model.UserInfo
import com.technopolis_education.globusapp.model.UserInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private val webClient = WebClient().getApi()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_page, container, false)
        val recyclerView: RecyclerView? = view.findViewById(R.id.user_friends)
        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView?.adapter = ProfileFriendsAdapter(fillFriends())

        val userNameSurname: TextView = view.findViewById(R.id.name_surname_field_profile)
        val userEmail: TextView = view.findViewById(R.id.e_mail_field_profile)

        val userTokenSave = context?.getSharedPreferences("USER TOKEN", Context.MODE_PRIVATE)
        var userToken = ""
        if (userTokenSave?.contains("UserToken") == true) {
            userToken = userTokenSave.getString("UserToken", "").toString()
        }

        Log.i("test", userToken)

        val getUser = RegResponse(
            userToken
        )

        val callUser = webClient.userInfo(getUser)
        callUser.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                userNameSurname.text =
                    response.body()?.objectToResponse?.name + " " + response.body()?.objectToResponse?.surname
                userEmail.text = response.body()?.objectToResponse?.email
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.i("test", "error $t")
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun fillFriends(): ArrayList<UserInfoResponse> {
        val data = ArrayList<UserInfoResponse>()
        (0..10).forEach { i ->
            data.add(
                UserInfoResponse(
                    i.toLong(),
                    "Name $i",
                    "Surname $i",
                    "email $i",
                    "password $i",
                    RegResponse("token $i")
                )
            )
        }
        return data
    }
}