package com.technopolis_education.globusapp.logic.adapter.empty.friends.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.databinding.ItemFriendsFriendsEmptyBinding

class EmptyFriendFriendsAdapter(
) : RecyclerView.Adapter<EmptyFriendFriendsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmptyFriendFriendsViewHolder {
        return EmptyFriendFriendsViewHolder(
            ItemFriendsFriendsEmptyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holderFriend: EmptyFriendFriendsViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 1
    }
}