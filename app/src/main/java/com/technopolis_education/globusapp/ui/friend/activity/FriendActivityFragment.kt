package com.technopolis_education.globusapp.ui.friend.activity

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
import com.technopolis_education.globusapp.databinding.FragmentFriendActivityBinding
import com.technopolis_education.globusapp.logic.adapter.profile.ProfileUserActivityAdapter
import com.technopolis_education.globusapp.model.UserActivity

class FriendActivityFragment : Fragment() {
    private val webClient = WebClient().getApi()
    private var userActivityList: ArrayList<UserActivity> = ArrayList()

    private lateinit var friendActivityViewModel: FriendActivityViewModel
    private var _binding: FragmentFriendActivityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userActivityList.clear()
        friendActivityViewModel =
            ViewModelProvider(this).get(FriendActivityViewModel::class.java)

        fillActivity()

        _binding = FragmentFriendActivityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //------------------------------------------------------//
        // Friend activity and filter
        val userActivity: RecyclerView = binding.friendActivity
        userActivity.layoutManager =
            LinearLayoutManager(context)
        userActivity.adapter = ProfileUserActivityAdapter(userActivityList)

        val adapter = ProfileUserActivityAdapter(userActivityList)
        binding.activitySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newFriend: String?): Boolean {
                binding.activitySearch.clearFocus()
                return false
            }

            override fun onQueryTextChange(newFriend: String?): Boolean {
                adapter.filter.filter(newFriend)
                return false
            }
        })

        userActivity.adapter = adapter
        //------------------------------------------------------//

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillActivity() {
        (0..10).forEach { i ->
            userActivityList.add(
                UserActivity(
                    "Title $i",
                    "Author $i",
                    "Content $i",
                    "Date $i"
                )
            )
        }
    }
}