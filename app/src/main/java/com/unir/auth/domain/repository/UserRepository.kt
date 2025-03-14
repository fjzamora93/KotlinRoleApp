package com.unir.auth.domain.repository

import com.unir.auth.data.model.User
import com.unir.character.data.model.remote.UserDTO

interface UserRepository {
    suspend fun getUser(): Result<User>
    suspend fun updateUser(user: UserDTO): Result<User>
    suspend fun deleteUser(): Result<Unit>
}