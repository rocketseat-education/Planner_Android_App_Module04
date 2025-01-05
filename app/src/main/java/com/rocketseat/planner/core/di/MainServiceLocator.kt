package com.rocketseat.planner.core.di

import android.app.Application
import androidx.room.Room
import com.rocketseat.planner.data.database.PLANNER_ACTIVITY_DATABASE_NAME
import com.rocketseat.planner.data.database.PlannerActivityDao
import com.rocketseat.planner.data.database.PlannerActivityDatabase
import com.rocketseat.planner.data.datasource.AuthenticationLocalDataSource
import com.rocketseat.planner.data.datasource.AuthenticationLocalDataSourceImpl
import com.rocketseat.planner.data.datasource.PlannerActivityLocalDataSource
import com.rocketseat.planner.data.datasource.PlannerActivityLocalDataSourceImpl
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

    val authenticationLocalDataSource: AuthenticationLocalDataSource by lazy {
        AuthenticationLocalDataSourceImpl(
            applicationContext = application.applicationContext
        )
    }

    val plannerActivityDao: PlannerActivityDao by lazy {
        val database = Room.databaseBuilder(
            application.applicationContext,
            PlannerActivityDatabase::class.java,
            PLANNER_ACTIVITY_DATABASE_NAME
        ).build()

        database.plannerActivityDao()
    }

    val plannerActivityLocalDataSource: PlannerActivityLocalDataSource by lazy {
        PlannerActivityLocalDataSourceImpl(
            plannerActivityDao = plannerActivityDao
        )
    }

    fun initializer(application: Application) {
        _application = application
    }

    fun clear() {
        _application = null
    }
}