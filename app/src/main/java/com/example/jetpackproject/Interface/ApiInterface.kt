package com.example.jetpackproject.Interface

import com.example.jetpackproject.Model.AuthResponse
import com.example.jetpackproject.Model.ChatHistoryResponse
import com.example.jetpackproject.Model.ChatResponse
import com.example.jetpackproject.Model.ChatSession
import com.example.jetpackproject.Model.LoginRequest
import com.example.jetpackproject.Model.RegisterRequest
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login") // Ye endpoint aapko FastAPI mein banana hoga
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("/chat")
    suspend fun getGeminiResponse(
        @Query("prompt") prompt: String,
        @Query("session_id") sessionId: String,
        @Query("user_id") userId: Int
    ): ChatResponse

    @GET("/history/{user_id}")
    suspend fun getHistory(
        @Path("user_id") userId: Int
    ): List<ChatSession>

    @GET("/messages/{session_id}")
    suspend fun getSessionMessages(
        @Path("session_id") sessionId: String
    ): List<ChatHistoryResponse> // Ek simple data class bana lena jisme prompt aur response ho
}

