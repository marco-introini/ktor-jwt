package me.mintdev.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.mintdev.routing.request.LoginRequest
import me.mintdev.service.JwtService

fun Route.loginRoute(jwtService: JwtService) {
    post {
        val loginRequest = call.receive<LoginRequest>()
        val token = jwtService.createJwtToken(loginRequest)
        if (token != null) {
            call.respond(HttpStatusCode.OK, token)
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }

        /* Versione del video
        token?.let {
            call.respond(hashMapOf("token" to it))
        } ?: call.respond (HttpStatusCode.Unauthorized)
        */
    }
}