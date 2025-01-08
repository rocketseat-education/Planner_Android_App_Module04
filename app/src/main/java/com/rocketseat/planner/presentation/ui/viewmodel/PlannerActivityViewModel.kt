package com.rocketseat.planner.presentation.ui.viewmodel

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocketseat.planner.core.di.MainServiceLocator
import com.rocketseat.planner.core.di.MainServiceLocator.ioDispatcher
import com.rocketseat.planner.core.di.MainServiceLocator.mainDispatcher
import com.rocketseat.planner.data.datasource.PlannerActivityLocalDataSource
import com.rocketseat.planner.domain.model.PlannerActivity
import com.rocketseat.planner.domain.utils.createCalendarFromTimeInMillis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class PlannerActivityViewModel : ViewModel() {

    val plannerActivityLocalDataSource: PlannerActivityLocalDataSource by lazy {
        MainServiceLocator.plannerActivityLocalDataSource
    }

    private val _activities: MutableStateFlow<List<PlannerActivity>> = MutableStateFlow(emptyList())
    val activities: StateFlow<List<PlannerActivity>> = _activities.asStateFlow()

    private val newActivity: MutableStateFlow<NewPlannerActivity> =
        MutableStateFlow(NewPlannerActivity())

    private val updateActivity: MutableStateFlow<PlannerActivity?> =
        MutableStateFlow(null)

    fun setSelectedActivity(selectedActivity: PlannerActivity) {
        updateActivity.value = selectedActivity
    }

    fun clearSelectedActivity() {
        updateActivity.value = null
    }

    fun updateSelectedActivity(
        name: String? = null,
        date: SetDate? = null,
        time: SetTime? = null
    ) {
        if (name == null && date == null && time == null) return

        updateActivity.update { currentUpdatedActivity ->
            currentUpdatedActivity?.let { currentUpdatedActivity ->
                val updateDateTimeCalendar =
                    createCalendarFromTimeInMillis(currentUpdatedActivity.dateTime)

                updateDateTimeCalendar.apply {
                    if (date != null) {
                        set(Calendar.YEAR, date.year)
                        set(Calendar.MONTH, date.month)
                        set(Calendar.DAY_OF_MONTH, date.dayOfMonth)
                    }
                    if (time != null) {
                        set(Calendar.HOUR_OF_DAY, time.hourOfDay)
                        set(Calendar.MINUTE, time.minute)
                    }
                }
                currentUpdatedActivity.copy(
                    name = name ?: currentUpdatedActivity.name,
                    dateTime = updateDateTimeCalendar.timeInMillis
                )
            }
        }
    }

    fun saveUpdatedSelectedActivity() {
        updateActivity.value?.let { selectedActivity ->
            update(selectedActivity)
            clearSelectedActivity()
        }
    }

    fun updateNewActivity(
        name: String? = null,
        date: SetDate? = null,
        time: SetTime? = null
    ) {
        if (name == null && date == null && time == null) return

        newActivity.update { currentNewActivity ->
            currentNewActivity.copy(
                name = name ?: currentNewActivity.name,
                date = date ?: currentNewActivity.date,
                time = time ?: currentNewActivity.time
            )
        }
    }

    fun saveNewActivity(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        newActivity.value.let { newActivity ->
            if (newActivity.isFilled()) {
                insert(
                    name = newActivity.name.orEmpty(),
                    dateTime = createNewFilledCalendar().timeInMillis
                )
                this@PlannerActivityViewModel.newActivity.update { NewPlannerActivity() }

                onSuccess()
            } else {
                onError()
            }
        }
    }

    private fun createNewFilledCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        return calendar.apply {
            newActivity.value.let { newActivity ->
                set(Calendar.YEAR, newActivity.date?.year ?: 0)
                set(Calendar.MONTH, newActivity.date?.month ?: 0)
                set(Calendar.DAY_OF_MONTH, newActivity.date?.dayOfMonth ?: 0)
                set(Calendar.HOUR_OF_DAY, newActivity.time?.hourOfDay ?: 0)
                set(Calendar.MINUTE, newActivity.time?.minute ?: 0)
            }
        }
    }

    fun fetchActivities() {
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

    private fun insert(name: String, dateTime: Long) {
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

    private fun update(plannerActivity: PlannerActivity) {
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
