package com.technopolis_education.globusapp.ui.profile.friends

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.databinding.FragmentProfileUserFriendsAddFriendBinding
import com.technopolis_education.globusapp.databinding.FragmentProfileUserFriendsBinding
import com.technopolis_education.globusapp.logic.adapter.profile.ProfileUserFriendsAdapter
import com.technopolis_education.globusapp.logic.interfaces.profile.OnFriendClickListener
import com.technopolis_education.globusapp.model.RegResponse
import com.technopolis_education.globusapp.model.UserInfoResponse

class UserFriendsFragment : Fragment(), OnFriendClickListener {

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
        userFriends.adapter = ProfileUserFriendsAdapter(context, userFriendsList, this)

        val adapter = ProfileUserFriendsAdapter(context, userFriendsList, this)
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

        val addFriendBtn = binding.addFriend
        addFriendBtn.setOnClickListener { addFriend() }

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
                    RegResponse("id $i", "token $i")
                )
            )
        }
    }

    // Find and add a friend
    private fun addFriend() {
        val addNewFriend =
            FragmentProfileUserFriendsAddFriendBinding.inflate(LayoutInflater.from(context))
        val friendEmail = addNewFriend.friendEmail
        val submitBtn = addNewFriend.submitFindFriend

        val addDialog = AlertDialog.Builder(context)
        addDialog.setView(addNewFriend.root)

        userFriendsList.add(
            UserInfoResponse(
                12,
                "afd",
                "sf",
                "asdf",
                "sadf",
                RegResponse("12", "ASDF")
            )
        )

        submitBtn.setOnClickListener {
            Toast.makeText(context, "Friend request send", Toast.LENGTH_SHORT)
                .show()
        }

        addDialog.create()
        addDialog.show()
    }

    override fun onFriendItemClick(item: UserInfoResponse, position: Int) {
        findNavController().navigate(R.id.action_profile_to_friendFragment)
    }
}