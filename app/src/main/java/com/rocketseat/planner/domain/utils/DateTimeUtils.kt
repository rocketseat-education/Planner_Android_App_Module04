package com.rocketseat.planner.domain.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.Locale

private val sdfPlannerActivityDateTime = SimpleDateFormat("EEE dd' \n'HH:mm", Locale("pt", "BR"))
private val sdfPlannerActivityDate = SimpleDateFormat("dd 'de' MMMM", Locale("pt", "BR"))
private val sdfPlannerActivityTime = SimpleDateFormat("HH:mm", Locale("pt", "BR"))

fun createCalendarFromTimeInMillis(timeMillis: Long): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeMillis
    return calendar
}

fun Calendar.toPlannerActivityDateTime(): String = sdfPlannerActivityDateTime.format(this)
fun Calendar.toPlannerActivityDate(): String = sdfPlannerActivityDate.format(this)
fun Calendar.toPlannerActivityTime(): String = sdfPlannerActivityTime.format(this)