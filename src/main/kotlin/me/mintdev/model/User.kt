package me.mintdev.model

import java.util.*

data class User(
    val id: UUID,
    val username: String,
    val password: String,
    ) {
}