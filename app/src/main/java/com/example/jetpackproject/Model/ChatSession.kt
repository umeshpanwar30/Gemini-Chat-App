package com.example.jetpackproject.Model

data class ChatSession(
    val session_id: String,
    val title: String?,
    val last_update: String
)

data class ChatMessage(
    val text: String,
    val isUser: Boolean
)
