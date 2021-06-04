package com.technopolis_education.globusapp.logic.adapter.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.databinding.ItemProfileFriendCardBinding
import com.technopolis_education.globusapp.model.UserInfoResponse

class ProfileUserFriendsAdapter(
    private val userFriends: ArrayList<UserInfoResponse>
) : RecyclerView.Adapter<ProfileUserFriendsViewHolder>(), Filterable {

    var userFriendsList: ArrayList<UserInfoResponse> = userFriends

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileUserFriendsViewHolder {
        return ProfileUserFriendsViewHolder(
            ItemProfileFriendCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileUserFriendsViewHolder, position: Int) {
        holder.friendName.text =
            userFriendsList[position].name + " " + userFriendsList[position].surname
        holder.friendEmail.text = userFriendsList[position].email
    }

    override fun getItemCount(): Int {
        return userFriendsList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                userFriendsList = if (charSearch.isEmpty()) {
                    userFriends
                } else {
                    val resultList = ArrayList<UserInfoResponse>()
                    for (friend in userFriends) {
                        if ((friend.name + " " + friend.surname)
                                .contains(charSearch.toLowerCase())
                        ) {
                            resultList.add(friend)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = userFriendsList
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                userFriendsList = results?.values as ArrayList<UserInfoResponse>
                notifyDataSetChanged()
            }
        }
    }
}