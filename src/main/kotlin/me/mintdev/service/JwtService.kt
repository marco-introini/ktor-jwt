package me.mintdev.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import me.mintdev.routing.request.LoginRequest
import java.util.*

class JwtService(
    private val application: Application,
    private val userService: UserService
) {

    private val secret = getConfigProperty("jwt.secret")
    private val issuer = getConfigProperty("jwt.issuer")
    private val audience = getConfigProperty("jwt.audience")
    val realm = getConfigProperty("jwt.realm")

    val jwtVerifier: JWTVerifier = JWT
        .require(Algorithm.HMAC256(secret))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun createJwtToken(loginRequest: LoginRequest): String? {
        val user = userService.findByUsername(loginRequest.username)
        val passwordMatches = user?.password == loginRequest.password
        return if (user != null && passwordMatches) {
            JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withClaim("username", user.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 3600 * 24 * 1000))
                .sign(Algorithm.HMAC256(secret))
        } else
            null
    }

    private fun extractUsername(credential: JWTCredential): String? = credential.payload.getClaim("username").asString()

    fun customValidator(credential: JWTCredential): JWTPrincipal? {
        val username = extractUsername(credential)
        val foundUser = username?.let { userService.findByUsername(it) }

        return foundUser?.let {
            if (audienceMatch(credential)) {
                JWTPrincipal(credential.payload)
            } else null
        }
    }

    private fun audienceMatch(credential: JWTCredential): Boolean = credential.payload.audience.contains(audience)

    private fun getConfigProperty(path: String): String = application.environment.config.property(path).getString()
}