package com.example.controller

import com.example.data.model.auth.LoginRequest
import com.example.data.model.auth.LoginResponse
import com.example.data.model.auth.RegisterRequest
import com.example.domain.usecase.GetUserUseCase
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.RegisterUseCase
import io.github.smiley4.ktoropenapi.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class AuthController(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getUserUseCase: GetUserUseCase,
) {
    fun configure(application: Application) {
        application.routing {
            post("/login", {
                tags = listOf("Authentication")
                summary = "Авторизация пользователя"
                description = "Принимает логин/пароль и возвращает JWT токен"

                request {
                    body<LoginRequest> {
                        description = "Данные для входа"
                    }
                }

                response {
                    HttpStatusCode.OK to {
                        description = "Успешный вход"
                        body<LoginResponse>()
                    }
                    HttpStatusCode.Unauthorized to {
                        description = "Неверные учетные данные"
                    }
                }
            }) {
                val request = call.receive<LoginRequest>()
                val token = loginUseCase.login(request.login, request.password)

                if (token != null) {
                    val user = getUserUseCase(request.login)
                    if (user != null) {
                        call.respond(
                            LoginResponse(
                                id = user.id,
                                login = user.login,
                                token = token
                            )
                        )
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Пользователь не найден"))
                    }
                } else {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Неверный логин или пароль"))
                }
            }

            post("/register", {
                tags = listOf("Authentication")
                summary = "Регистрация нового пользователя"
                request { body<RegisterRequest>() }
                response {
                    HttpStatusCode.Created to { description = "Пользователь создан" }
                    HttpStatusCode.BadRequest to { description = "Логин уже занят" }
                }
            }) {
                val request = call.receive<RegisterRequest>()
                val user = registerUseCase(request.login, request.password)

                if (user != null) {
                    call.respond(HttpStatusCode.Created, mapOf("status" to "success"))
                } else {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Логин занят"))
                }
            }
        }
    }
}