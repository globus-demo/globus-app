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
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.databinding.FragmentProfileBinding
import com.technopolis_education.globusapp.model.RegResponse
import com.technopolis_education.globusapp.model.UserInfo
import com.technopolis_education.globusapp.ui.profile.activity.UserActivityFragment
import com.technopolis_education.globusapp.ui.profile.friends.UserFriendsFragment
import com.technopolis_education.globusapp.ui.profile.statistics.UserStatisticsFragment
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

        //------------------------------------------------------//
        // User info
        val userNameSurname: TextView = binding.nameSurnameFieldProfile
        val userEmail: TextView = binding.eMailFieldProfile

        val userTokenSave = context?.getSharedPreferences("USER TOKEN", Context.MODE_PRIVATE)
        val userIdSave = context?.getSharedPreferences("USER ID", Context.MODE_PRIVATE)
        var userToken = ""
        var userId = ""
        if (userTokenSave?.contains("UserToken") == true) {
            userToken = userTokenSave.getString("UserToken", "").toString()
        }
        if (userIdSave?.contains("UserId") == true){
            userId = userIdSave.getString("UserId", "").toString()
        }

        val getUser = RegResponse(
            userId,
            userToken
        )

        Log.i("test", getUser.toString())

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
        //------------------------------------------------------//

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.profile_container, UserActivityFragment())
            ?.commit()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.showFriends.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.profile_container, UserFriendsFragment())
                ?.commit()
        }
        binding.showActivity.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.profile_container, UserActivityFragment())
                ?.commit()
        }
        binding.showStatistic.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.profile_container, UserStatisticsFragment())
                ?.commit()
        }
    }
}