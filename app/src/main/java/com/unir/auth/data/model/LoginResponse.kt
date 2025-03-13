package com.unir.auth.data.model

import com.unir.sheet.data.remote.model.ApiUser
import java.util.Date

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: ApiUser,
    val expiration: Date
)