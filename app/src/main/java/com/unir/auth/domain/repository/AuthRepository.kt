package com.unir.auth.domain.repository

import com.unir.auth.data.model.User
import com.unir.character.data.model.remote.UserDTO

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun autoLogin(): Result<User>
    suspend fun signup(email: String, password: String): Result<User>
    suspend fun logout(): Result<Unit>
}