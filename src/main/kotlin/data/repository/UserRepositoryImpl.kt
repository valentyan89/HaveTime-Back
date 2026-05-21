package com.example.data.repository

import com.example.data.database.tables.UsersTable
import com.example.data.mapper.toDomain
import com.example.data.mapper.toUserDto
import com.example.data.model.user.UserDto
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserRepositoryImpl(): UserRepository {
    override suspend fun createUser(login: String, passwordHash: String): User = newSuspendedTransaction {
        val inserted = UsersTable.insert {
            it[UsersTable.login] = login
            it[UsersTable.passwordHash] = passwordHash
        }

        UserDto(
            id = inserted[UsersTable.id].value,
            login = inserted[UsersTable.login],
            passwordHash = inserted[UsersTable.passwordHash]
        ).toDomain()
    }

    override suspend fun findByLogin(login: String): User? = newSuspendedTransaction {
        UsersTable.selectAll().where { UsersTable.login eq login }
            .map{
                it.toUserDto().toDomain()
            }.firstOrNull()
    }

    override suspend fun findById(id: Int): User? = newSuspendedTransaction {
        UsersTable.selectAll().where { UsersTable.id eq id }
            .map{
                it.toUserDto().toDomain()
            }.firstOrNull()
    }

    override suspend fun getPasswordHash(login: String): String? = newSuspendedTransaction {
        UsersTable.selectAll().where { UsersTable.login eq login }
            .map {
                it[UsersTable.passwordHash]
            }.firstOrNull()
    }
}