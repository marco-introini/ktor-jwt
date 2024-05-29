package me.mintdev.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*
import me.mintdev.service.JwtService
import me.mintdev.service.UserService

fun Application.configureRouting(
    userService: UserService,
    jwtService: JwtService
) {
    routing {

        route("/login") {
            loginRoute(jwtService)
        }

        route("/api/user") {
            userRoute(userService)
        }
    }
}
