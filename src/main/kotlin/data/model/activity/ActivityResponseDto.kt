package com.example.data.model.activity

import kotlinx.serialization.Serializable

@Serializable
data class ActivityResponseDto(
    val id: Int,
    val userId: Int,
    val title: String,
    val color: Int,
    val startTime: Long,
    val endTime: Long,
    val lat: Double?,
    val lon: Double?,
    val address: String?,
    val updatedAt: Long,
    val isDeleted: Boolean = false
)