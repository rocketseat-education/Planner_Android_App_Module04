package com.rocketseat.planner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocketseat.planner.core.di.MainServiceLocator
import com.rocketseat.planner.data.datasource.PlannerActivityLocalDataSource
import com.rocketseat.planner.domain.model.PlannerActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class PlannerActivityViewModel: ViewModel() {

    val plannerActivityLocalDataSource: PlannerActivityLocalDataSource by lazy {
        MainServiceLocator.plannerActivityLocalDataSource
    }

    private val _activities: MutableStateFlow<List<PlannerActivity>> = MutableStateFlow(emptyList())
    val activities: StateFlow<List<PlannerActivity>> = _activities.asStateFlow()

    init {
        viewModelScope.launch {
            plannerActivityLocalDataSource.plannerActivities.collect { activities ->
                _activities.value = activities
            }
        }
    }

    fun insertPlannerActivity(name: String, dateTime: Long) {
        val plannerActivity = PlannerActivity(
            uuid = UUID.randomUUID().toString(),
            name = name,
            dateTime = dateTime,
            isCompleted = false
        )
        plannerActivityLocalDataSource.insert(plannerActivity = plannerActivity)
    }

    fun updatePlannerActivity(plannerActivity: PlannerActivity) {
        plannerActivityLocalDataSource.update(plannerActivity = plannerActivity)
    }

    fun updateIsCompleted(uuid: String, isCompleted: Boolean) {
        plannerActivityLocalDataSource.updateIsCompletedByUuid(uuid = uuid, isCompleted = isCompleted)
    }

    fun deletePlannerActivity(uuid: String) {
        plannerActivityLocalDataSource.deleteByUuid(uuid = uuid)
    }
}