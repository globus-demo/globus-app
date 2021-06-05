package com.technopolis_education.globusapp.logic.interfaces.profile

import com.technopolis_education.globusapp.model.UserInfoResponse

interface OnFriendClickListener {
    fun onFriendItemClick(item: UserInfoResponse, position: Int)
}