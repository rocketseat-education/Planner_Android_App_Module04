package com.rocketseat.planner.data.datasource

interface UserRegistrationLocalDataSource {
    fun getUserRegistered(): Boolean
    fun saveIsUserRegistered(isUserRegistered: Boolean)
}