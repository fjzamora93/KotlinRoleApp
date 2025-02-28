package com.unir.sheet.data.remote.model

import java.util.Date

data class LoginResponse(
    val token: String,
    val user: ApiUser,
    val expiration: Date
)