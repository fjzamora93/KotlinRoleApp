package com.unir.auth.data.model

import com.unir.sheet.data.remote.model.UserDTO
import java.util.Date

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDTO,
    val expiration: Date
)