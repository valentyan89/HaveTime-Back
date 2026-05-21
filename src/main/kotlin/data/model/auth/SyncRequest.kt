package com.example.data.model.auth

import com.example.data.model.activity.ActivityResponseDto
import kotlinx.serialization.Serializable

@Serializable
data class SyncRequest(
    val activities: List<ActivityResponseDto>,
    val lastSync: Long
)
