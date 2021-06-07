package com.technopolis_education.globusapp.logic.adapter.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.databinding.ItemFeedPostBinding
import com.technopolis_education.globusapp.model.UserActivity

class FeedPostsAdapter(
    private val activity: ArrayList<UserActivity>,
) : RecyclerView.Adapter<FeedPostsViewHolder>(), Filterable {

    var activityList: ArrayList<UserActivity> = activity

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedPostsViewHolder {
        return FeedPostsViewHolder(
            ItemFeedPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedPostsViewHolder, position: Int) {
        holder.title?.text = activityList[position].title
        holder.content?.text = activityList[position].content
        holder.creator?.text = activityList[position].author
        holder.country?.text = activityList[position].country
        holder.date?.text = activityList[position].date
    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                activityList = if (charSearch.isEmpty()) {
                    activity
                } else {
                    val resultList = ArrayList<UserActivity>()
                    for (activity in activityList) {
                        if ((activity.title)
                                .contains(charSearch.toLowerCase())
                        ) {
                            resultList.add(activity)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = activityList
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                activityList = results?.values as ArrayList<UserActivity>
                notifyDataSetChanged()
            }
        }
    }
}
