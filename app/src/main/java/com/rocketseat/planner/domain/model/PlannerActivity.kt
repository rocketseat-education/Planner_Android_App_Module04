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

    // X - aberto/fechado
    fun calculatePriority(priority: String): Int {
        return when (priority) {
            "Alta" -> 3
            "Média" -> 2
            "Baixa" -> 1
            "Muito Alta" -> 4
            else -> 0
        }
    }

    // OK
    interface TaskPriority {
        fun getPriotity(): Int
    }

    class HighPriority : TaskPriority {
        override fun getPriotity(): Int = 3
    }

    class MediumPriority : TaskPriority {
        override fun getPriotity(): Int = 2
    }

    class LowPriority : TaskPriority {
        override fun getPriotity(): Int = 1
    }

    class VeryHighPriority : TaskPriority {
        override fun getPriotity(): Int = 4
    }

    fun calculatePriority(priority: TaskPriority): Int = priority.getPriotity()
}
