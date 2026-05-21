package com.example.domain.usecase

import com.example.domain.model.Activity
import com.example.domain.repository.ActivityRepository

class SyncUseCase(private val activityRepository: ActivityRepository, private val getActivitiesAfterTimeUseCase: GetActivitiesAfterTimeUseCase) {
    suspend operator fun invoke(userId: Int, activities: List<Activity>, lastSync: Long): List<Activity> {
        if (activities.isNotEmpty()) activityRepository.updateInsertActivities(userId, activities)
        return getActivitiesAfterTimeUseCase(userId, lastSync)
    }
}