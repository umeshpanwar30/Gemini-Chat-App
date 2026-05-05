package com.example.jetpackproject.Model

import com.google.gson.annotations.SerializedName

// ChatHistoryResponse.kt
data class ChatHistoryResponse(
    val prompt: String,    // User ka purana sawal
    val response: String   // AI ka purana jawab
)
