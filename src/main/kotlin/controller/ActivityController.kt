package com.example.controller

import com.example.data.mapper.toDomain
import com.example.data.mapper.toDto
import com.example.data.model.activity.ActivityResponseDto
import com.example.data.model.auth.SyncRequest
import com.example.domain.usecase.SyncUseCase
import com.example.domain.usecase.DeleteActivityUseCase
import com.example.domain.usecase.GetActivitiesUseCase
import io.github.smiley4.ktoropenapi.post
import io.github.smiley4.ktoropenapi.delete
import io.github.smiley4.ktoropenapi.get
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class ActivityController(
    private val syncUseCase: SyncUseCase,
    private val deleteActivityUseCase: DeleteActivityUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase
) {
    fun configure(application: Application) {
        application.routing {
            authenticate("auth-jwt") {
                route("/activities"){
                    get("/", {
                        tags = listOf("Calendar")
                        summary = "Получить все активности"
                        description = "Выгрузить все активности пользователя, которые доступны"
                        securitySchemeNames = listOf("MyJwtAuth")

                        response {
                            HttpStatusCode.OK to {
                                description = "Список всех активностей"
                                body<List<ActivityResponseDto>>()
                            }
                        }
                    }) {
                        val principal = call.principal<JWTPrincipal>()
                        val userId = principal?.payload?.getClaim("id")?.asInt() ?: return@get call.respond(HttpStatusCode.Unauthorized)
                        val activities = getActivitiesUseCase(userId)

                        call.respond(activities.map { it.toDto() })
                    }

                    post("/sync", {
                        tags = listOf("Calendar")
                        summary = "Синхронизация активностей"
                        description = "Принимает список изменений от клиента и возвращает свежие данные с сервера"
                        securitySchemeNames = listOf("MyJwtAuth")

                        request {
                            body<SyncRequest> {
                                description = "Данные для синхронизации"
                            }
                        }

                        response {
                            HttpStatusCode.OK to {
                                description = "Список обновленных активностей"
                                body<List<ActivityResponseDto>>()
                            }
                            HttpStatusCode.Unauthorized to { description = "Неавторизован" }
                        }
                    }) {
                        val principal = call.principal<JWTPrincipal>()
                        val userId = principal?.payload?.getClaim("id")?.asInt() ?: return@post call.respond(HttpStatusCode.Unauthorized)
                        val request = call.receive<SyncRequest>()

                        val freshActivities = syncUseCase(
                            userId = userId,
                            activities = request.activities.map { it.toDomain() },
                            lastSync = request.lastSync
                        )

                        call.respond(freshActivities.map { it.toDto() })
                    }

                    delete("/{id}", {
                        tags = listOf("Calendar")
                        summary = "Удаление активности"
                        securitySchemeNames = listOf("MyJwtAuth")

                        response {
                            HttpStatusCode.OK to { description = "Удалено" }
                            HttpStatusCode.NotFound to { description = "Запись не найдена" }
                        }
                    }) {
                        val principal = call.principal<JWTPrincipal>()
                        val userId = principal?.payload?.getClaim("id")?.asInt() ?: return@delete call.respond(HttpStatusCode.Unauthorized)
                        val activityId = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)

                        if (deleteActivityUseCase(userId, activityId)) {
                            call.respond(HttpStatusCode.OK, mapOf("status" to "Успешно удалено"))
                        } else {
                            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Не найдена активность"))
                        }
                    }
                }
            }
        }
    }
}