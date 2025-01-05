package com.rocketseat.planner.domain.model

import android.icu.util.Calendar
import com.rocketseat.planner.domain.utils.createCalendarFromTimeInMillis
import com.rocketseat.planner.domain.utils.toPlannerActivityDate
import com.rocketseat.planner.domain.utils.toPlannerActivityDateTime
import com.rocketseat.planner.domain.utils.toPlannerActivityTime


data class PlannerActivity(
    val uuid: String,
    val name: String,
    val dateTime: Long,
    val isCompleted: Boolean,
) {
    private val dateTimerCalendar: Calendar = createCalendarFromTimeInMillis(dateTime)

    val dateString: String = dateTimerCalendar.toPlannerActivityDate()
    val timeString: String = dateTimerCalendar.toPlannerActivityTime()
    val dateTimeString: String = dateTimerCalendar.toPlannerActivityDateTime()
}
