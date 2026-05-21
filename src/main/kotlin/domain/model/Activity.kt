package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Activity(
    val id: Int,
    val userId: Int,
    val title: String,
    val startTime: Long,
    val endTime: Long,
    val lat: Double?,
    val lon: Double?,
    val address: String?,
    val updatedAt: Long
)