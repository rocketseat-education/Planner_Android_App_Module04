package com.rocketseat.planner.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.rocketseat.planner.data.datasource.UserRegistrationLocalDataSource
import com.rocketseat.planner.data.di.MainServiceLocator

class UserRegistrationViewModel: ViewModel() {

    private val userRegistrationLocalDataSource: UserRegistrationLocalDataSource by lazy {
        MainServiceLocator.userRegistrationLocalDataSource
    }

    fun getIsUserRegistered(): Boolean {
        return userRegistrationLocalDataSource.getUserRegistered()
    }

    fun saveIsUserRegistered(isUserRegistered: Boolean) {
        userRegistrationLocalDataSource.saveIsUserRegistered(isUserRegistered = isUserRegistered)
    }
}