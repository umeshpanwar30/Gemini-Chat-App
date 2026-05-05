package com.example.jetpackproject.Object

import com.example.jetpackproject.Interface.ApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {


    private const val BASE_URL = "http://192.168.1.194:8000/" // FastAPI ka local address

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS) // Connection ke liye 1 min
        .readTimeout(60, TimeUnit.SECONDS)    // Data read karne ke liye 1 min
        .writeTimeout(60, TimeUnit.SECONDS)   // Data bhejne ke liye 1 min
        .build()


    val apiService: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}