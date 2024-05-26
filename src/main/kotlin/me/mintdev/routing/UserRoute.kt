package me.mintdev.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.mintdev.model.User
import me.mintdev.routing.request.UserRequest
import me.mintdev.service.UserService
import java.util.*

fun Route.userRoute(
    userService: UserService
) {
    post {
        val userRequest = call.receive<UserRequest>()

        val savedUser = userService.save(
            user = userRequest.toModel()
        ) ?: return@post call.respond(HttpStatusCode.BadRequest)
    }

}

private fun UserRequest.toModel(): User = User(
    id = UUID.randomUUID(),
    username = this.username,
    password = this.password,
)