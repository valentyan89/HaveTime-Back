package com.example.domain.usecase

import com.example.domain.model.Activity
import com.example.domain.repository.ActivityRepository

class GetActivitiesUseCase(private val activityRepository: ActivityRepository) {
    suspend operator fun invoke(userId: Int): List<Activity> = activityRepository.getActivities(userId)
}