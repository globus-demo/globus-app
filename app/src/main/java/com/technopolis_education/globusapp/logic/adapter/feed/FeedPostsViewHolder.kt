package com.technopolis_education.globusapp.logic.adapter.feed

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.databinding.ItemFeedPostBinding

class FeedPostsViewHolder(
    binding: ItemFeedPostBinding
) : RecyclerView.ViewHolder(binding.root) {
    var title: TextView? = null
    var content: TextView? = null
    var creator: TextView? = null
    var date: TextView? = null

    init {
        title = binding.activityTitle
        content = binding.activityContent
        creator = binding.activityAuthor
        date = binding.activityDate
    }
}