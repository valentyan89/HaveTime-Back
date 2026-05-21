package com.example.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val id: Int,
    val login: String,
    val token: String
)
