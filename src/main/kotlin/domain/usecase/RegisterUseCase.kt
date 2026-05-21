package com.example.domain.usecase

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.security.PasswordHasher

class RegisterUseCase(private val userRepository: UserRepository, private val passwordHasher: PasswordHasher) {
    suspend operator fun invoke(login: String, password: String): User? {
        val user = userRepository.findByLogin(login)
        if (user != null) return null
        val passwordHash = passwordHasher.hash(password)
        return userRepository.createUser(login, passwordHash)
    }
}