package com.rocketseat.planner.data.datasource

interface UserRegistrationLocalDataSource {
    fun getUserRegistration(): Boolean
    fun saveUserRegistration(isUserRegistered: Boolean)
}