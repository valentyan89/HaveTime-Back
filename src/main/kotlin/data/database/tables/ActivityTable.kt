package com.example.data.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object ActivityTable: IntIdTable("activities") {
    val userId = reference("client_id", UsersTable)
    val title = text("title")
    val startTime = long("start_time")
    val endTime = long("end_time")
    val latitude = double("latitude").nullable()
    val longitude = double("longitude").nullable()
    val address = text("address").nullable()
    val updatedAt = long("updated_at")
}