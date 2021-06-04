package com.technopolis_education.globusapp.logic.adapter.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.databinding.ItemProfileActiivityCardBinding
import com.technopolis_education.globusapp.model.UserActivity

class ProfileUserActivityAdapter(
    private val userActivity: ArrayList<UserActivity>
) : RecyclerView.Adapter<ProfileUserActivityViewHolder>(), Filterable {

    var userActivityList: ArrayList<UserActivity> = userActivity

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileUserActivityViewHolder {
        return ProfileUserActivityViewHolder(
            ItemProfileActiivityCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileUserActivityViewHolder, position: Int) {
        holder.activityAuthor.text = userActivityList[position].author
        holder.activityTitle.text = userActivityList[position].title
        holder.activityContent.text = userActivityList[position].content
        holder.activityDate.text = userActivityList[position].date
    }

    override fun getItemCount(): Int {
        return userActivityList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                userActivityList = if (charSearch.isEmpty()) {
                    userActivity
                } else {
                    val resultList = ArrayList<UserActivity>()
                    for (activity in userActivity) {
                        if ((activity.title)
                                .contains(charSearch.toLowerCase())
                        ) {
                            resultList.add(activity)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = userActivityList
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                userActivityList = results?.values as ArrayList<UserActivity>
                notifyDataSetChanged()
            }
        }
    }
}