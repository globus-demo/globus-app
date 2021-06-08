package com.technopolis_education.globusapp.model

 data class FriendInfoResponse(
     val status: Boolean,
     val text: String,
     val objectToResponse: FriendsInfo
 )