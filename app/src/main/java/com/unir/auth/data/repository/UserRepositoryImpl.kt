package com.unir.auth.data.repository

import com.unir.auth.data.model.User
import com.unir.auth.data.service.UserApiService
import com.unir.auth.domain.repository.UserRepository
import com.unir.auth.security.TokenManager
import com.unir.auth.data.model.UserDTO
import javax.inject.Inject

class UserRepositoryImpl  @Inject constructor(
    private val api: UserApiService,
    private val tokenManager: TokenManager
) : UserRepository {

    // Obtener usuario (usando Access Token)
    override suspend fun getUser(): Result<User> {
        return try {
            val token = tokenManager.getAccessToken() ?: return Result.failure(Exception("No hay Access Token"))
            val response = api.getUser("Bearer $token")

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toUserEntity())
            } else {
                Result.failure(Exception("Error obteniendo usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Actualizar usuario
    override suspend fun updateUser(user: UserDTO): Result<User> {
        return try {
            val token = tokenManager.getAccessToken() ?: return Result.failure(Exception("No hay Access Token"))
            val response = api.updateUser("Bearer $token", user)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toUserEntity())
            } else {
                Result.failure(Exception("Error actualizando usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Eliminar usuario
    override suspend fun deleteUser(): Result<Unit> {
        return try {
            val token = tokenManager.getAccessToken() ?: return Result.failure(Exception("No hay Access Token"))
            val response = api.deleteUser("Bearer $token")

            if (response.isSuccessful) {
                tokenManager.clearTokens() // Borra ambos tokens
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error eliminando usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
