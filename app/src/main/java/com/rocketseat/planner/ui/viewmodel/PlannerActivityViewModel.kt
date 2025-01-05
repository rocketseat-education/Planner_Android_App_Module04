package com.rocketseat.planner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocketseat.planner.core.di.MainServiceLocator
import com.rocketseat.planner.core.di.MainServiceLocator.ioDispatcher
import com.rocketseat.planner.core.di.MainServiceLocator.mainDispatcher
import com.rocketseat.planner.data.datasource.PlannerActivityLocalDataSource
import com.rocketseat.planner.domain.model.PlannerActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class PlannerActivityViewModel: ViewModel() {

    val plannerActivityLocalDataSource: PlannerActivityLocalDataSource by lazy {
        MainServiceLocator.plannerActivityLocalDataSource
    }

    private val _activities: MutableStateFlow<List<PlannerActivity>> = MutableStateFlow(emptyList())
    val activities: StateFlow<List<PlannerActivity>> = _activities.asStateFlow()

    init {
        viewModelScope.launch {
            plannerActivityLocalDataSource.plannerActivities
                .flowOn(ioDispatcher)
                .collect { activities ->
                    withContext(mainDispatcher) {
                        _activities.value = activities
                    }
                }
        }
    }

    fun insertPlannerActivity(name: String, dateTime: Long) {
        viewModelScope.launch {
            val plannerActivity = PlannerActivity(
                uuid = UUID.randomUUID().toString(),
                name = name,
                dateTime = dateTime,
                isCompleted = false
            )
            withContext(ioDispatcher) {
                plannerActivityLocalDataSource.insert(plannerActivity = plannerActivity)
            }
        }
    }

    fun updatePlannerActivity(plannerActivity: PlannerActivity) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                plannerActivityLocalDataSource.update(plannerActivity = plannerActivity)
            }
        }
    }

    fun updateIsCompleted(uuid: String, isCompleted: Boolean) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                plannerActivityLocalDataSource.updateIsCompletedByUuid(
                    uuid = uuid,
                    isCompleted = isCompleted
                )
            }
        }
    }

    fun deletePlannerActivity(uuid: String) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                plannerActivityLocalDataSource.deleteByUuid(uuid = uuid)
            }
        }
    }
}