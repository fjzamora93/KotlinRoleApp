package com.unir.sheet.data.remote.model

data class LoginResponse(
    val user: ApiUser,
    val token: String
)