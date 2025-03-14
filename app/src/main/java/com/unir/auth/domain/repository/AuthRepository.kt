package com.unir.auth.domain.repository

import com.unir.auth.data.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun autoLogin(): Result<User>
    suspend fun signup(email: String, password: String): Result<User>
    suspend fun logout(): Result<Unit>
}