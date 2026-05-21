package com.example.domain.usecase

import com.example.domain.model.User
import com.example.domain.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(login: String): User? = userRepository.findByLogin(login)
    suspend fun getById(id: Int): User? = userRepository.findById(id)
}