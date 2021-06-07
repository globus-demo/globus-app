package com.technopolis_education.globusapp.logic.adapter.empty.profile.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.databinding.ItemProfileFriendsEmptyBinding

class EmptyProfileFriendsAdapter(
) : RecyclerView.Adapter<EmptyProfileFriendsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmptyProfileFriendsViewHolder {
        return EmptyProfileFriendsViewHolder(
            ItemProfileFriendsEmptyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holderProfile: EmptyProfileFriendsViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 1
    }
}