package com.example.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    val id: Int,
    val login: String
)
