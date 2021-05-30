package com.technopolis_education.globusapp.logic.adapter.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.model.UserInfoResponse

class ProfileFriendsAdapter(
    private val userFriends : ArrayList<UserInfoResponse>
): RecyclerView.Adapter<ProfileFriendsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileFriendsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.friend_card, parent, false)
        return ProfileFriendsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProfileFriendsViewHolder, position: Int) {
        holder.friendName?.text = userFriends[position].name + " " + userFriends[position].surname
        holder.friendEmail?.text = userFriends[position].email
    }

    override fun getItemCount(): Int {
        return userFriends.size
    }

}