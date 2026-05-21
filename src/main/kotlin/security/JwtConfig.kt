package com.example.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import java.util.Date

object JwtConfig {
    private const val SECRET = "our-super-secret-key"
    private const val ISSUER = "activity-ktor"
    private const val AUDIENCE = "mobile-app"
    private const val VALIDITY = 7L * 24 * 60 * 60 * 1000
    private val algorithm = Algorithm.HMAC256(SECRET)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withAudience(AUDIENCE)
        .withIssuer(ISSUER)
        .build()

    fun generateToken(login: String, id: Int): String {
        return JWT.create()
            .withAudience(AUDIENCE)
            .withIssuer(ISSUER)
            .withClaim("login", login)
            .withClaim("id", id)
            .withExpiresAt(Date(System.currentTimeMillis() + VALIDITY))
            .sign(algorithm)
    }
}