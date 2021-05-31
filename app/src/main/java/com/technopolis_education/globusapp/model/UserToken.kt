package com.technopolis_education.globusapp.model

data class UserToken(
    val status: Boolean,
    val text: String,
    val objectToResponse: RegResponse
)
