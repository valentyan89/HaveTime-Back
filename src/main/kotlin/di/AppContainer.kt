package com.example.di

import com.example.controller.AuthController
import com.example.data.repository.ActivityRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.ActivityRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.DeleteActivityUseCase
import com.example.domain.usecase.GetActivitiesAfterTimeUseCase
import com.example.domain.usecase.GetActivitiesUseCase
import com.example.domain.usecase.GetUserUseCase
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.RegisterUseCase
import com.example.domain.usecase.SyncUseCase
import com.example.security.PasswordHasher

object AppContainer {
    val userRepository: UserRepository by lazy { UserRepositoryImpl() }
    val activityRepository: ActivityRepository by lazy { ActivityRepositoryImpl() }

    val passwordHasher: PasswordHasher by lazy { PasswordHasher }

    val loginUseCase: LoginUseCase by lazy { LoginUseCase(userRepository, passwordHasher) }
    val registerUseCase: RegisterUseCase by lazy { RegisterUseCase(userRepository, passwordHasher) }
    val getUserUseCase: GetUserUseCase by lazy { GetUserUseCase(userRepository) }

    val getActivitiesUseCase: GetActivitiesUseCase by lazy { GetActivitiesUseCase(activityRepository) }
    val getActivitiesAfterTimeUseCase: GetActivitiesAfterTimeUseCase by lazy { GetActivitiesAfterTimeUseCase(activityRepository) }
    val syncUseCase: SyncUseCase by lazy { SyncUseCase(activityRepository) }
    val deleteActivityUseCase: DeleteActivityUseCase by lazy { DeleteActivityUseCase(activityRepository) }

    val authController: AuthController by lazy { AuthController(loginUseCase, registerUseCase, getUserUseCase) }
}