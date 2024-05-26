package me.mintdev.routing.response

import kotlinx.serialization.Serializable
import me.mintdev.utils.UUIDSerializer
import java.util.UUID

@Serializable
data class UserResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val username: String,
)
