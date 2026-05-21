package com.example.security

import at.favre.lib.crypto.bcrypt.BCrypt

object PasswordHasher {
    fun hash(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    fun verify(hash: String, password: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), hash).verified
    }
}