package com.technopolis_education.globusapp.ui.friend.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.api.WebClient
import com.technopolis_education.globusapp.databinding.FragmentFriendFriendsBinding
import com.technopolis_education.globusapp.logic.adapter.profile.friends.FriendFriendsAdapter
import com.technopolis_education.globusapp.logic.interfaces.profile.OnFriendClickListener
import com.technopolis_education.globusapp.model.RegResponse
import com.technopolis_education.globusapp.model.UserInfoResponse

class FriendFriendsFragment : Fragment(), OnFriendClickListener {

    private val webClient = WebClient().getApi()
    private var userFriendsList: ArrayList<UserInfoResponse> = ArrayList()

    private lateinit var friendFriendsViewModel: FriendFriendsViewModel
    private var _binding: FragmentFriendFriendsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userFriendsList.clear()
        friendFriendsViewModel =
            ViewModelProvider(this).get(FriendFriendsViewModel::class.java)

        _binding = FragmentFriendFriendsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fillFriends()

        //------------------------------------------------------//
        // Friend friends and filter
        val userFriends: RecyclerView = binding.friendFriends
        userFriends.layoutManager =
            LinearLayoutManager(context)
        userFriends.adapter = FriendFriendsAdapter(userFriendsList, this)

        val adapter = FriendFriendsAdapter(userFriendsList, this)
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
                    RegResponse("id $i", "token $i")
                )
            )
        }
    }

    override fun onFriendItemClick(item: UserInfoResponse, position: Int) {
        findNavController().navigate(R.id.action_friendFragment_self)
    }
}