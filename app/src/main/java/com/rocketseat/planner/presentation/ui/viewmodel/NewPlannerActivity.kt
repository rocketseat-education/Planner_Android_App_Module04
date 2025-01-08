package com.rocketseat.planner.presentation.ui.viewmodel

data class NewPlannerActivity(
    val name: String? = null,
    val date: SetDate? = null,
    val time: SetTime? = null
) {
    fun isFilled(): Boolean = !name.isNullOrEmpty() && date != null && time != null
}

data class SetDate(
    val dayOfMonth: Int? = null,
    val month: Int? = null,
    val year: Int? = null
)

data class SetTime(
    val hourOfDay: Int? = null,
    val minute: Int? = null
)