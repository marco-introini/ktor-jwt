package me.mintdev.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
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
