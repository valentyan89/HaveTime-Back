package com.example.domain.repository

import com.example.domain.model.Activity

interface ActivityRepository {
    suspend fun getActivities(userId: Int): List<Activity>
    suspend fun getActivitiesAfterTime(userId: Int, lastUpdateTime: Long): List<Activity>
    suspend fun updateInsertActivities(userId: Int, activities: List<Activity>): Boolean
    suspend fun deleteActivity(userId: Int, activityId: Int): Boolean
}