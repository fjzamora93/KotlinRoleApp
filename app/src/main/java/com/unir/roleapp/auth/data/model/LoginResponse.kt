package com.roleapp.auth.data.model

import java.util.Date

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDTO,
    val expiration: Date
)