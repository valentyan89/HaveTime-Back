package com.example.data.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UsersTable: IntIdTable("users") {
    val login = varchar("login", 32).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val createdAt = long("created_at").clientDefault { System.currentTimeMillis() }
}