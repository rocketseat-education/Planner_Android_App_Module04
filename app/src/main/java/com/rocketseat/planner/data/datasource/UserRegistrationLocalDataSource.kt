package com.rocketseat.planner.data.datasource

import com.rocketseat.planner.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface UserRegistrationLocalDataSource {
    fun getUserRegistered(): Boolean
    fun saveIsUserRegistered(isUserRegistered: Boolean)
    val profile: Flow<Profile>
    suspend fun saveProfile(profile: Profile)
}