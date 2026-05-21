package com.example.routing

import com.example.di.AppContainer
import io.github.smiley4.ktoropenapi.openApi
import io.github.smiley4.ktorredoc.redoc
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureRoutes() {
    routing {
        route("/api.json") {
            openApi()
        }
        // Роут с интерфейсом ReDoc
        route("/docs") {
            redoc("/api.json") { // указываем на роут выше
                pageTitle = "My API Docs"
            }
        }
        get("/test") {
            call.respond(mapOf("message" to "Это открытый маршрут!"))
        }
        authenticate("auth-jwt") {
            get("/secret") {
                call.respond(mapOf("message" to "Если ты это видишь, твой токен работает!"))
            }
        }
    }
    AppContainer.authController.configure(this)
    AppContainer.activityController.configure(this)
}