package me.mintdev.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*
import me.mintdev.service.UserService

fun Application.configureRouting(
    userService: UserService
) {
    routing {
        route("/api/user") {
            userRoute(userService)
        }
    }
}
