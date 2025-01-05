package com.rocketseat.planner.data.model

data class Profile(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val image: String = ""
) {
    fun isValid(): Boolean {
        return name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && image.isNotEmpty()
    }
}