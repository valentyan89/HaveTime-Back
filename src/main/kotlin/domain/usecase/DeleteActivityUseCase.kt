package com.example.domain.usecase

import com.example.domain.repository.ActivityRepository

class DeleteActivityUseCase(private val activityRepository: ActivityRepository) {
    suspend operator fun invoke(userId: Int, activityId: Int): Boolean = activityRepository.deleteActivity(userId, activityId)
}