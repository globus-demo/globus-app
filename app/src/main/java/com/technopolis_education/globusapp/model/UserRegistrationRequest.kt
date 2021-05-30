package com.technopolis_education.globusapp.model

data class UserRegistrationRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)
