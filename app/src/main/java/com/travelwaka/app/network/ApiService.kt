package com.travelwaka.app.network

import com.travelwaka.app.network.model.AuthResponse
import com.travelwaka.app.network.model.LoginRequest
import com.travelwaka.app.network.model.MeResponse
import com.travelwaka.app.network.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): AuthResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("auth/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    ): AuthResponse

    @GET("auth/me")
    suspend fun me(
        @Header("Authorization") token: String
    ): MeResponse
}