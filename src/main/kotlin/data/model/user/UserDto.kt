package com.example.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val login: String,
    val passwordHash: String
)
