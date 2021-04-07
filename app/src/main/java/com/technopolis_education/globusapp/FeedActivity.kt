package com.technopolis_education.globusapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.db.posts.PostViewModel
import kotlinx.android.synthetic.main.feed_activity.view.*

class FeedActivity : Fragment() {

    private lateinit var postViewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.feed_activity, container, false)

        val adapter = PostsRecyclerAdapter()
        val recyclerView: RecyclerView = view.feed_recycler_view
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        postViewModel.readAllData.observe(viewLifecycleOwner, Observer { posts ->
            adapter.setData(posts)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}