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
    val lat: Double? = null,
    val lon: Double? = null,
    val address: String? = null,
    val updatedAt: Long = 0L,
    val isDeleted: Boolean = false
)