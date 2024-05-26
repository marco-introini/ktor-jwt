package me.mintdev.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.mintdev.model.User
import me.mintdev.routing.request.UserRequest
import me.mintdev.routing.response.UserResponse
import me.mintdev.service.UserService
import java.util.*

fun Route.userRoute(
    userService: UserService
) {
    // add a new user
    post {
        val userRequest = call.receive<UserRequest>()

        val savedUser = userService.save(
            user = userRequest.toModel()
        ) ?: return@post call.respond(HttpStatusCode.BadRequest)

        call.response.header(
            name = "id",
            value = savedUser.id.toString()
        )

        call.respond(HttpStatusCode.Created)
    }

    // get all users
    get {

        val users = userService.findAll()
        call.respond(
            message = users.map(User::toResponse)
         )

    }

    // get a single user
    get("/{id}") {
        val id: String = call.parameters["id"]
            ?: return@get call.respond(HttpStatusCode.BadRequest)

        val foundUser: User = userService.findById(id)
            ?: return@get call.respond(HttpStatusCode.NotFound)

        call.respond(
            message = foundUser.toResponse()
        )
    }

}

private fun UserRequest.toModel(): User = User(
    id = UUID.randomUUID(),
    username = this.username,
    password = this.password,
)

private fun User.toResponse(): UserResponse =
    UserResponse(
        id = this.id,
        username = this.username
    )
