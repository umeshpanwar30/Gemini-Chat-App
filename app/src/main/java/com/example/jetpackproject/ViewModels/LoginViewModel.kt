package com.example.jetpackproject.ViewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackproject.Model.LoginRequest
import com.example.jetpackproject.Model.LoginUiState
import com.example.jetpackproject.Object.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// 1. Constructor mein 'val' lagao taaki ye pure class mein accessible ho
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun performLogin(email: String, pass: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)
            try {
                val response = RetrofitClient.apiService.login(LoginRequest(email, pass))

                if (response.isSuccessful && response.body() != null) {
                    val authData = response.body()!!

                    val sharedPref = getApplication<Application>().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putBoolean("isLoggedIn", true)
                        putString("authToken", authData.token)

                        // Agar backend se ID aayi hai toh wahi save karo, warna default 1
                        putInt("userId", authData.user_id ?: 1)

                        apply()
                    }
                    _uiState.value = LoginUiState(isLoginSuccess = true)
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState(error = "Network Error: ${e.message}")
            }
        }
    }
}