package com.example.domain.usecase


import com.example.domain.repository.UserRepository
import com.example.security.JwtConfig
import com.example.security.PasswordHasher

class LoginUseCase(private val userRepository: UserRepository, private val passwordHasher: PasswordHasher) {
    suspend fun login(login: String, password: String): String? {
        val user = userRepository.findByLogin(login) ?: return null
        val passwordHash = userRepository.getPasswordHash(login) ?: return null
        if (!passwordHasher.verify(passwordHash, password)) return null
        return JwtConfig.generateToken(user.login, user.id)
    }
}