package com.example.data.database

import com.example.data.database.tables.ActivityTable
import com.example.data.database.tables.UsersTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(){
        val dotenv = dotenv()
        val dbUrl = dotenv["DATABASE_URL"]
        val dbUsername = dotenv["DATABASE_USERNAME"]
        val dbPassword = dotenv["DATABASE_PASSWORD"]

        val config = HikariConfig().apply {
            jdbcUrl = dbUrl
            username = dbUsername
            password = dbPassword
            driverClassName = "org.postgresql.Driver"
            isAutoCommit = false
            maximumPoolSize = 3
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }

        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(UsersTable, ActivityTable)


        }

        println("Database initialization complete!")
    }
}