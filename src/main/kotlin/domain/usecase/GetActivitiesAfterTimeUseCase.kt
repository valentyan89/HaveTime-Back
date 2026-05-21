package com.example.domain.usecase

import com.example.domain.model.Activity
import com.example.domain.repository.ActivityRepository

class GetActivitiesAfterTimeUseCase(private val activityRepository: ActivityRepository) {
    suspend operator fun invoke(userId: Int, lastSync: Long): List<Activity> = activityRepository.getActivitiesAfterTime(userId, lastSync)
}