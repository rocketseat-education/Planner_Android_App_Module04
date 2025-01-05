package com.rocketseat.planner.data.model

import kotlinx.serialization.Serializable

@Serializable
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