package com.technopolis_education.globusapp.db.messages

data class Message(
    val sender: Int,
    val receiver: Int,
    val content: String,
)