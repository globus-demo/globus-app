package com.technopolis_education.globusapp.logic.adapter.error

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.databinding.ItemNoInternetErrorBinding

class InternetErrorAdapter(
): RecyclerView.Adapter<InternetErrorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InternetErrorViewHolder {
        return InternetErrorViewHolder(
            ItemNoInternetErrorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: InternetErrorViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 1
    }
}