package com.rocketseat.planner.data.di

import android.app.Application
import com.rocketseat.planner.data.datasource.UserRegistrationLocalDataSource
import com.rocketseat.planner.data.datasource.UserRegistrationLocalDataSourceImpl

object MainServiceLocator {

    private var _application: Application? = null
    private val application: Application get() = _application!!

    val userRegistrationLocalDataSource: UserRegistrationLocalDataSource by lazy {
        UserRegistrationLocalDataSourceImpl(
            applicationContext = application.applicationContext
        )
    }

    fun initializer(application: Application) {
        _application = application
    }

    fun clear() {
        _application = null
    }
}