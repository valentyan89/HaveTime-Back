package com.example.domain.repository

import com.example.domain.model.User

interface UserRepository {
    suspend fun createUser(login: String, passwordHash: String): User
    suspend fun findByLogin(login: String): User?
    suspend fun findById(id: Int): User?
}