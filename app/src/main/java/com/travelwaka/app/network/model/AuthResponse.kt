package com.travelwaka.app.network.model

data class AuthResponse(
    val status: Boolean,
    val message: String,
    val data: AuthData?
)

data class AuthData(
    val user: User,
    val token: String
)

data class MeResponse(
    val status: Boolean,
    val message: String,
    val data: User?
)