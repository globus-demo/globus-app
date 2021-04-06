package com.technopolis_education.globusapp.db

data class Message (
    val sender: Int,
    val receiver: Int,
    val content: String,
)