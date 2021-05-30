package com.technopolis_education.globusapp.logic.adapter.profile

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.R

class ProfileFriendsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var friendName: TextView? = itemView.findViewById(R.id.friend_name)
    var friendEmail: TextView? = itemView.findViewById(R.id.friend_email)
}