package com.example.plugins

import io.ktor.server.application.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.security.JwtConfig
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.respond

fun Application.configureSecurity() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = "Ktor Server"
            verifier(JwtConfig.verifier)
            validate { credential ->
                val id = credential.payload.getClaim("id").asInt()
                val expTime = credential.payload.expiresAt?.time ?: 0
                val nowTime = System.currentTimeMillis()
                val expired = expTime < nowTime

                println("JWT validate:")
                println("id: $id")
                println("expired: $expired")

                if (id != null && !expired) {
                    JWTPrincipal(credential.payload)
                } else null
            }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid or expired token"))
            }
        }
    }
}