package com.technopolis_education.globusapp.ui.profile.activity

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
import com.technopolis_education.globusapp.databinding.FragmentProfileUserActivityBinding
import com.technopolis_education.globusapp.logic.adapter.profile.ProfileUserActivityAdapter
import com.technopolis_education.globusapp.model.UserActivity

class UserActivityFragment : Fragment() {

    private val webClient = WebClient().getApi()
    private var userActivityList: ArrayList<UserActivity> = ArrayList()

    private lateinit var userActivityViewModel: UserActivityViewModel
    private var _binding: FragmentProfileUserActivityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userActivityList.clear()
        userActivityViewModel =
            ViewModelProvider(this).get(UserActivityViewModel::class.java)

        fillActivity()

        _binding = FragmentProfileUserActivityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //------------------------------------------------------//
        // User activity and filter
        val userActivity: RecyclerView = binding.userActivity
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
                    "Country $i",
                    "Date $i"
                )
            )
        }
    }
}