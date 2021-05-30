package com.technopolis_education.globusapp.model

data class UserInfoResponse(
    val id: Long,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val token: RegResponse
)
