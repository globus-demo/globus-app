package com.technopolis_education.globusapp.ui.feed

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
import com.technopolis_education.globusapp.databinding.FragmentFeedBinding
import com.technopolis_education.globusapp.logic.adapter.feed.FeedPostsAdapter
import com.technopolis_education.globusapp.model.UserActivity

class FeedFragment : Fragment() {

    private val webClient = WebClient().getApi()
    private var userActivityList: ArrayList<UserActivity> = ArrayList()

    private lateinit var galleryViewModel: FeedViewModel
    private var _binding: FragmentFeedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        galleryViewModel =
            ViewModelProvider(this).get(FeedViewModel::class.java)

        fillActivity()

        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = FeedPostsAdapter(userActivityList)
        val recyclerView: RecyclerView = binding.feedRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        binding.activitySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.activitySearch.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
               adapter.filter.filter(p0)
                return false
            }
        })

        recyclerView.adapter = adapter

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
                    "County $i",
                    "Date $i"
                )
            )
        }
    }
}