package com.example.data.mapper

import com.example.data.database.tables.ActivityTable
import com.example.data.model.activity.ActivityResponseDto
import com.example.domain.model.Activity
import org.jetbrains.exposed.sql.ResultRow

fun ActivityResponseDto.toDomain(): Activity {
    return Activity(
        id = id,
        userId = userId,
        title = title,
        color = color,
        startTime = startTime,
        endTime = endTime,
        lat = lat,
        lon = lon,
        address = address,
        isDeleted = isDeleted,
        updatedAt = updatedAt
    )
}

fun ResultRow.toActivityDto(): ActivityResponseDto {
    return ActivityResponseDto(
        id = this[ActivityTable.id].value,
        userId = this[ActivityTable.userId].value,
        title = this[ActivityTable.title],
        color = this[ActivityTable.color],
        startTime = this[ActivityTable.startTime],
        endTime = this[ActivityTable.endTime],
        lat = this[ActivityTable.latitude],
        lon = this[ActivityTable.longitude],
        address = this[ActivityTable.address],
        isDeleted = this[ActivityTable.isDeleted],
        updatedAt = this[ActivityTable.updatedAt]
    )
}

fun Activity.toDto(): ActivityResponseDto {
    return ActivityResponseDto(
        id = id,
        userId = userId,
        title = title,
        color = color,
        startTime = startTime,
        endTime = endTime,
        lat = lat,
        lon = lon,
        address = address,
        isDeleted = isDeleted,
        updatedAt = updatedAt
    )
}