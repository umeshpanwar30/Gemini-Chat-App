package com.example.jetpackproject.Model

data class LoginUiState(

    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoginSuccess: Boolean = false,
)
