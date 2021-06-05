package com.technopolis_education.globusapp.logic.adapter.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.technopolis_education.globusapp.R
import com.technopolis_education.globusapp.databinding.ItemFriendFriendCardBinding
import com.technopolis_education.globusapp.databinding.ItemProfileFriendCardBinding
import com.technopolis_education.globusapp.logic.interfaces.profile.OnFriendClickListener
import com.technopolis_education.globusapp.model.UserInfoResponse

class ProfileUserFriendsAdapter(
    private val context: Context?,
    private val userFriends: ArrayList<UserInfoResponse>,
    private val clickListener: OnFriendClickListener
) : RecyclerView.Adapter<ProfileUserFriendsAdapter.ProfileUserFriendsViewHolder>(), Filterable {

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

        holder.initializeFriend(userFriendsList[position], clickListener)
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

    inner class ProfileUserFriendsViewHolder(
        binding: ItemProfileFriendCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var friendName: TextView = binding.friendName
        var friendEmail: TextView = binding.friendEmail
        var menu: ImageView = binding.editFriend

        fun initializeFriend(item: UserInfoResponse, action: OnFriendClickListener) {
            itemView.setOnClickListener {
                action.onFriendItemClick(item, adapterPosition)
            }
            menu.setOnClickListener {
                popupMenu(it, item)
            }
        }

        private fun popupMenu(view: View, item: UserInfoResponse) {
            val popupMenus = android.widget.PopupMenu(context, view)
            popupMenus.inflate(R.menu.menu_friend)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> {
                        Toast.makeText(context, "Friend removed", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    else -> true
                }
            }
            popupMenus.show()
            val popup = android.widget.PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
    }
}