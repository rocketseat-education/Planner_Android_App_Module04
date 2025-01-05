package com.rocketseat.planner.data.datasource

import kotlinx.coroutines.flow.Flow

interface AuthenticationLocalDataSource {
    val token: Flow<String>
    val expirationDataTime: Flow<Long>
    suspend fun insertToken(token: String)
}