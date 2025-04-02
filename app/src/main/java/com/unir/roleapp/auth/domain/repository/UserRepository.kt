package com.roleapp.auth.domain.repository

import com.roleapp.auth.data.model.User

interface UserRepository {
    suspend fun getUser(): Result<User>
    suspend fun updateUser(user: User): Result<User>
    suspend fun deleteUser(): Result<Unit>
}