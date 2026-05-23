package com.example

import com.example.data.database.DatabaseFactory
import com.example.di.appModule
import com.example.plugins.*
import com.example.routing.configureRoutes
import io.github.smiley4.ktoropenapi.OpenApi
import io.github.smiley4.ktoropenapi.config.AuthScheme
import io.github.smiley4.ktoropenapi.config.AuthType
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(OpenApi) {
        info {
            title = "Calendar activities API"
            version = "1.0.0"
        }
        server {
            url = "http://localhost:8080"
            description = "Локальный сервер"
        }
        server {
            url = "http://0.0.0.1:8080"
            description = "Локальный сервер для мобилки"
        }
        security {
            securityScheme("auth-jwt") {
                type = AuthType.HTTP
                scheme = AuthScheme.BEARER
                bearerFormat = "JWT"
            }
        }
    }

    DatabaseFactory.init()

    appModule()
    configureContentNegotiation()
    configureCallLogging()
    configureStatusPages()
    configureSecurity()
    configureRoutes()
}