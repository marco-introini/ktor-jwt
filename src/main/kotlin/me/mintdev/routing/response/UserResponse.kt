package me.mintdev.routing.response

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserResponse(
    val id: UUID,
    val username: String,
)
