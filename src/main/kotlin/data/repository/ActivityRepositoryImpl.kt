package com.example.data.repository

import com.example.data.database.tables.ActivityTable
import com.example.data.mapper.toActivityDto
import com.example.data.mapper.toDomain
import com.example.domain.model.Activity
import com.example.domain.repository.ActivityRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class ActivityRepositoryImpl: ActivityRepository {
    override suspend fun getActivities(userId: Int): List<Activity> = newSuspendedTransaction {
        ActivityTable.selectAll().where { (ActivityTable.userId eq userId) and (ActivityTable.isDeleted eq false) }
            .map {
                it.toActivityDto().toDomain()
            }
    }

    override suspend fun getActivitiesAfterTime(userId: Int, lastUpdateTime: Long): List<Activity> = newSuspendedTransaction {
        ActivityTable.selectAll().where { (ActivityTable.userId eq userId) and (ActivityTable.updatedAt greater lastUpdateTime) }
            .map {
                it.toActivityDto().toDomain()
            }
    }

    override suspend fun updateInsertActivities(userId: Int, activities: List<Activity>): Boolean = newSuspendedTransaction {
        try {
            activities.forEach { activity ->
                val existingActivity = ActivityTable.selectAll().where { ActivityTable.id eq activity.id }.firstOrNull()

                if (existingActivity == null) {
                    if (activity.isDeleted) return@forEach

                    ActivityTable.insert {
                        it[id] = activity.id
                        it[ActivityTable.userId] = userId
                        it[title] = activity.title
                        it[color] = activity.color
                        it[startTime] = activity.startTime
                        it[endTime] = activity.endTime
                        it[latitude] = activity.lat
                        it[longitude] = activity.lon
                        it[address] = activity.address
                        it[updatedAt] = System.currentTimeMillis()
                        it[isDeleted] = false
                    }
                } else {
                    val ownerId = existingActivity[ActivityTable.userId].value

                    if (ownerId == userId) {
                        ActivityTable.update(where = { ActivityTable.id eq activity.id }) {
                            it[title] = activity.title
                            it[color] = activity.color
                            it[startTime] = activity.startTime
                            it[endTime] = activity.endTime
                            it[latitude] = activity.lat
                            it[longitude] = activity.lon
                            it[address] = activity.address
                            it[updatedAt] = System.currentTimeMillis()
                            it[isDeleted] = activity.isDeleted
                        }
                    } else {
                        println("User $userId tried to overwrite activity of user $ownerId")
                    }
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun deleteActivity(userId: Int, activityId: Int): Boolean = newSuspendedTransaction {
        val rowsUpdated = ActivityTable.update({ (ActivityTable.userId eq userId) and (ActivityTable.id eq activityId) }) {
            it[isDeleted] = true
            it[updatedAt] = System.currentTimeMillis()
        }
        rowsUpdated > 0
    }
}