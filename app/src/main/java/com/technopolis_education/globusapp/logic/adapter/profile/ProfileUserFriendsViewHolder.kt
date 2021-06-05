package com.technopolis_education.globusapp.logic.adapter.profile

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.databinding.ItemProfileFriendCardBinding
import com.technopolis_education.globusapp.logic.interfaces.profile.OnFriendClickListener
import com.technopolis_education.globusapp.model.UserInfoResponse

class ProfileUserFriendsViewHolder(
    binding: ItemProfileFriendCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    var friendName: TextView = binding.friendName
    var friendEmail: TextView = binding.friendEmail

    fun initializeFriend(item: UserInfoResponse, action: OnFriendClickListener) {
        itemView.setOnClickListener {
            action.onFriendItemClick(item, adapterPosition)
        }
    }
}