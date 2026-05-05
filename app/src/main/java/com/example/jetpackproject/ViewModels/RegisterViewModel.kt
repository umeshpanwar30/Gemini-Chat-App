package com.example.jetpackproject.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackproject.Model.LoginUiState
import com.example.jetpackproject.Model.RegisterRequest
import com.example.jetpackproject.Object.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()


    fun performRegister(name: String, email: String, pass: String) {

        viewModelScope.launch {
            // Step 1: Loading state dikhao
            _uiState.value = LoginUiState(isLoading = true)

            try {
                // Step 2: Register API call karo (FastAPI endpoint: /auth/register)
                val request = RegisterRequest(name = name, email = email, password = pass)
                val response = RetrofitClient.apiService.register(request)

                // Step 3: Response handle karo
                if (response.isSuccessful) {
                    // Agar 201 Created ya 200 OK aaya
                    _uiState.value =
                        LoginUiState(isLoginSuccess = true) // Iska naam isRegisterSuccess bhi rakh sakte ho
                } else {
                    // Agar email pehle se exist karta hai ya validation fail hui
                    val errorMsg = response.errorBody()?.string() ?: "Registration Failed"
                    _uiState.value = LoginUiState(error = errorMsg)
                }
            } catch (e: Exception) {
                // Agar internet nahi hai ya server down hai
                _uiState.value = LoginUiState(error = "Network Error: ${e.message}")
            }
        }
    }

}