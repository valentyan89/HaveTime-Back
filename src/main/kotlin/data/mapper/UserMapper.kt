package com.example.data.mapper

import com.example.data.database.tables.UsersTable
import com.example.data.model.user.UserDto
import com.example.domain.model.User
import org.jetbrains.exposed.sql.ResultRow

fun UserDto.toDomain(): User {
    return User(id = id, login = login)
}

fun ResultRow.toUserDto(): UserDto {
    return UserDto(
        id = this[UsersTable.id].value,
        login = this[UsersTable.login],
        passwordHash = this[UsersTable.passwordHash]
    )
}