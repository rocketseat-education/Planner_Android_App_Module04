package com.rocketseat.planner.data.datasource

import com.rocketseat.planner.domain.model.PlannerActivity
import kotlinx.coroutines.flow.Flow

interface PlannerActivityLocalDataSource {
    val plannerActivities: Flow<List<PlannerActivity>>
    suspend fun insert(plannerActivity: PlannerActivity)
    suspend fun getByUuid(uuid: String): PlannerActivity
    suspend fun updateIsCompletedByUuid(uuid: String, isCompleted: Boolean)
    suspend fun update(plannerActivity: PlannerActivity)
    suspend fun deleteByUuid(uuid: String)
}