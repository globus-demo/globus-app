package com.technopolis_education.globusapp.logic.adapter.profile.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.databinding.ItemFriendFriendCardBinding
import com.technopolis_education.globusapp.logic.interfaces.profile.OnFriendClickListener
import com.technopolis_education.globusapp.model.UserInfoResponse

class FriendFriendsAdapter(
    private val friendFriends: ArrayList<UserInfoResponse>,
    private val clickListener: OnFriendClickListener
) : RecyclerView.Adapter<FriendFriendsViewHolder>(), Filterable {

    var friendFriendsList: ArrayList<UserInfoResponse> = friendFriends

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendFriendsViewHolder {
        return FriendFriendsViewHolder(
            ItemFriendFriendCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendFriendsViewHolder, position: Int) {
        holder.friendName.text =
            friendFriendsList[position].name + " " + friendFriendsList[position].surname
        holder.friendEmail.text = friendFriendsList[position].email

        holder.initializeFriend(friendFriendsList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return friendFriendsList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                friendFriendsList = if (charSearch.isEmpty()) {
                    friendFriends
                } else {
                    val resultList = ArrayList<UserInfoResponse>()
                    for (friend in friendFriends) {
                        if ((friend.name + " " + friend.surname)
                                .contains(charSearch.toLowerCase())
                        ) {
                            resultList.add(friend)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = friendFriendsList
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                friendFriendsList = results?.values as ArrayList<UserInfoResponse>
                notifyDataSetChanged()
            }
        }
    }
}