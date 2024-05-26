package me.mintdev

import io.ktor.server.application.*
import me.mintdev.plugins.*
import me.mintdev.repository.UserRepository
import me.mintdev.routing.configureRouting
import me.mintdev.service.UserService

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val userRepository = UserRepository()
    val userService = UserService(userRepository)

    configureSerialization()
    configureSecurity()
    configureRouting(userService)
}
