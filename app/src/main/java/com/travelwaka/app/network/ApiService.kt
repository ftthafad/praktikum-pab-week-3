package com.travelwaka.app.network

import com.travelwaka.app.network.model.AuthResponse
import com.travelwaka.app.network.model.CategoryListResponse
import com.travelwaka.app.network.model.LoginRequest
import com.travelwaka.app.network.model.MeResponse
import com.travelwaka.app.network.model.RegisterRequest
import com.travelwaka.app.network.model.WisataDetailResponse
import com.travelwaka.app.network.model.WisataListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // ✅ Auth
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

    // ✅ Wisata
    @GET("wisata")
    suspend fun getWisata(): WisataListResponse

    @GET("wisata/{id}")
    suspend fun getWisataDetail(
        @Path("id") id: Int
    ): WisataDetailResponse

    @GET("wisata/category/{categoryId}")
    suspend fun getWisataByCategory(
        @Path("categoryId") categoryId: Int
    ): WisataListResponse

    // ✅ Categories
    @GET("categories")
    suspend fun getCategories(): CategoryListResponse
}