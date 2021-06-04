package com.technopolis_education.globusapp.ui.profile.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.databinding.FragmentProfileUserFriendsBinding
import com.technopolis_education.globusapp.logic.adapter.profile.ProfileUserFriendsAdapter
import com.technopolis_education.globusapp.model.RegResponse
import com.technopolis_education.globusapp.model.UserInfoResponse

class UserFriendsFragment : Fragment() {

    private val webClient = WebClient().getApi()
    private var userFriendsList: ArrayList<UserInfoResponse> = ArrayList()

    private lateinit var userFriendsViewModel: UserFriendsViewModel
    private var _binding: FragmentProfileUserFriendsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userFriendsList.clear()
        userFriendsViewModel =
            ViewModelProvider(this).get(UserFriendsViewModel::class.java)

        _binding = FragmentProfileUserFriendsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fillFriends()

        //------------------------------------------------------//
        // User friends and filter
        val userFriends: RecyclerView = binding.userFriends
        userFriends.layoutManager =
            LinearLayoutManager(context)
        userFriends.adapter = ProfileUserFriendsAdapter(userFriendsList)

        val adapter = ProfileUserFriendsAdapter(userFriendsList)
        binding.friendsSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newFriend: String?): Boolean {
                binding.friendsSearch.clearFocus()
                return false
            }

            override fun onQueryTextChange(newFriend: String?): Boolean {
                adapter.filter.filter(newFriend)
                return false
            }
        })

        userFriends.adapter = adapter
        //------------------------------------------------------//

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillFriends() {
        (0..10).forEach { i ->
            userFriendsList.add(
                UserInfoResponse(
                    i.toLong(),
                    "Name $i",
                    "Surname $i",
                    "email $i",
                    "password $i",
                    RegResponse("id $i","token $i")
                )
            )
        }
    }
}