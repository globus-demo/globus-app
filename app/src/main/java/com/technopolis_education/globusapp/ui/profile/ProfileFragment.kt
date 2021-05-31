package com.technopolis_education.globusapp.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.databinding.FragmentProfileBinding
import com.technopolis_education.globusapp.logic.adapter.profile.ProfileFriendsAdapter
import com.technopolis_education.globusapp.model.RegResponse
import com.technopolis_education.globusapp.model.UserInfo
import com.technopolis_education.globusapp.model.UserInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private val webClient = WebClient().getApi()

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.userFriends
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ProfileFriendsAdapter(fillFriends())

        val userNameSurname: TextView = binding.nameSurnameFieldProfile
        val userEmail: TextView = binding.eMailFieldProfile

        val userTokenSave = context?.getSharedPreferences("USER TOKEN", Context.MODE_PRIVATE)
        var userToken = ""
        if (userTokenSave?.contains("UserToken") == true) {
            userToken = userTokenSave.getString("UserToken", "").toString()
        }

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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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