package com.rocketseat.planner.domain.model

import kotlinx.serialization.Serializable

@Serializable
open class Profile(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val image: String = ""
) {
    fun isValid(): Boolean {
        return name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && image.isNotEmpty()
    }
}

// X - Liskov
open class FeatureAccess {

    fun accessPlannerActivityHistory() {
        // ...
    }

    fun accessAdminBoard() {
        // ...
    }
}

// OK
interface CommonUserAccess {
    fun accessPlannerActivityHistory()

    // ...
}

interface AdminUserAccess {
    fun accessAdminBoard()

    // ...
}

open class CommonUserProfile(
    name: String = "",
    email: String = "",
    phone: String = "",
    image: String = ""
) : Profile(
    name,
    email,
    phone,
    image
), CommonUserAccess {
    override fun accessPlannerActivityHistory() {
        // ...
    }
}

class AdminUserProfile(
    name: String = "",
    email: String = "",
    phone: String = "",
    image: String = ""
) : CommonUserProfile(
    name,
    email,
    phone,
    image
), AdminUserAccess {
    override fun accessAdminBoard() {
        // ...
    }
}

fun main () {
//    val commonUserProfile = Profile()
    val adminUserProfile = AdminUserProfile()
    val commonUserProfile = CommonUserProfile()

    commonUserProfile.accessPlannerActivityHistory()
}