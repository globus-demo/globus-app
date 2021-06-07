package com.technopolis_education.globusapp.logic.adapter.profile

import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.databinding.ItemProfileActiivityCardBinding

class ProfileUserActivityViewHolder(binding: ItemProfileActiivityCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val activityTitle = binding.activityTitle
    val activityAuthor = binding.activityAuthor
    val activityContent = binding.activityContent
    val activityCountry = binding.activityCountry
    val activityDate = binding.activityDate
}