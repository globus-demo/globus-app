package com.technopolis_education.globusapp.logic.interfaces.profile

import com.technopolis_education.globusapp.model.FriendsInfo

interface OnFriendClickListener {
    fun onFriendItemClick(item: FriendsInfo, position: Int)
}