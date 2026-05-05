package com.example.jetpackproject.Model

data class AuthResponse(
    val message: String,
    val token: String? = null,
    val user_id: Int? = null,    // FastAPI se aane wali ID
    val user_email: String? = null
)
