package com.rocketseat.planner.data

/// X - responsabilidade única
class UserManager {

    fun registerUser(name: String, email: String) {
        // ...
    }

    fun sendEmail(email: String, message: String) {
        // ...
    }

}

// OK
class UserService {

    fun registerUser(name: String, email: String) {
        // ...
    }
}

class EmailService {

    fun sendEmail(email: String, message: String) {
        // ...
    }
}