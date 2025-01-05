package com.rocketseat.planner.data.datasource

import android.content.Context
import android.content.SharedPreferences

private const val USER_REGISTRATION_FILE_NAME = "user_registration"
private const val IS_USER_REGISTERED = "isUserRegistered"

class UserRegistrationLocalDataSourceImpl(
    private val applicationContext: Context
): UserRegistrationLocalDataSource {

    val userRegistrationSharedPreference: SharedPreferences =
        applicationContext.getSharedPreferences(USER_REGISTRATION_FILE_NAME, Context.MODE_PRIVATE)

    override fun getUserRegistered(): Boolean {
        return userRegistrationSharedPreference.getBoolean(IS_USER_REGISTERED, false)
    }

    override fun saveIsUserRegistered(isUserRegistered: Boolean) {
        with(userRegistrationSharedPreference.edit()) {
            putBoolean(IS_USER_REGISTERED, isUserRegistered)
            apply()
        }
    }
}