package me.mintdev.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import me.mintdev.service.JwtService

fun Application.configureSecurity(jwtService: JwtService) {
    authentication {
        jwt {
            realm = jwtService.realm
            verifier(jwtService.jwtVerifier)
            validate { credential -> jwtService.customValidator(credential)
            }
        }
    }
}
