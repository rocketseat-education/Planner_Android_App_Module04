package com.rocketseat.planner

import android.app.Application
import com.rocketseat.planner.data.di.MainServiceLocator

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        MainServiceLocator.initializer(
            application = this
        )
    }
}