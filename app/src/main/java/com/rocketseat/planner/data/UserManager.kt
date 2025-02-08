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

interface Service {
    val baseUrl: String
    fun run()

    // X - segregação de interface
//    fun registerUser(name: String, email: String)
//    fun sendEmail(email: String, message: String)
}

// OK - segregação de interface

interface UserServiceAction {
    fun registerUser(name: String, email: String)

    // ..
}

interface EmailServiceAction {
    fun sendEmail(email: String, message: String)

    // ..
}

class UserService(override val baseUrl: String) : Service, UserServiceAction {
    override fun run() {
        TODO("Not yet implemented")
    }

    override fun registerUser(name: String, email: String) {
        // ...
    }
}

class EmailService(override val baseUrl: String) : Service, EmailServiceAction {

    override fun sendEmail(email: String, message: String) {
        // ...
    }

    override fun run() {
        TODO("Not yet implemented")
    }
}