package com.example.jetpackproject.Model

import com.google.gson.annotations.SerializedName

data class ChatResponse(
    @SerializedName("response") // Yeh ensure karta hai ki 'response' key match kare
    val response: String?
)
