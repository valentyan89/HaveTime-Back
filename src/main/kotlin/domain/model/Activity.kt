package com.example.domain.model

data class Activity(
    val id: Int,
    val userId: Int,
    val title: String,
    val color: Int,
    val startTime: Long,
    val endTime: Long,
    val lat: Double?,
    val lon: Double?,
    val address: String?,
    val updatedAt: Long
)