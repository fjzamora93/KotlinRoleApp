package com.roleapp.auth.data.repository

import com.roleapp.auth.data.dao.UserDao
import com.roleapp.auth.data.model.User
import com.roleapp.auth.data.service.UserApiService
import com.roleapp.auth.domain.repository.UserRepository
import com.roleapp.auth.security.TokenManager
import javax.inject.Inject

class UserRepositoryImpl  @Inject constructor(
    private val api: UserApiService,
    private val tokenManager: TokenManager,
    private val userDao: UserDao
) : UserRepository {

    // Obtener usuario (usando Access Token)
    override suspend fun getUser(): Result<User> {
        return try {
            // Intentar obtener el token de acceso
            val token = tokenManager.getAccessToken()

            if (token.isNullOrEmpty()) {
                // Si no hay token, intentar obtener el usuario desde la base de datos local
                val localUser = userDao.getUser()
                if (localUser != null) {
                    return Result.success(localUser)
                } else {
                    // Si no hay usuario local, devolver un error adecuado
                    return Result.failure(Exception("No se encontró el usuario local ni el token"))
                }
            }

            // Si hay token, intentar obtener el usuario desde la API
            val response = api.getUser("Bearer $token")

            if (response.isSuccessful && response.body() != null) {
                return Result.success(response.body()!!.toUserEntity())
            } else {
                // Si la API falla, intenta obtener el usuario local
                val localUser = userDao.getUser()
                if (localUser != null) {
                    return Result.success(localUser)
                } else {
                    // Si no hay usuario local, devolver un error adecuado
                    return Result.failure(Exception("Error obteniendo usuario desde la API y no hay usuario local"))
                }
            }
        } catch (e: Exception) {
            // Manejo de cualquier excepción que ocurra durante el proceso
            println("Error: ${e.message}")
            return Result.failure(e)
        }
    }


    // Actualizar usuario
    override suspend fun updateUser(user: User): Result<User> {
        return try {
            val token = tokenManager.getAccessToken() ?: return Result.failure(Exception("No hay Access Token"))
            val response = api.updateUser("Bearer $token", user.toUserApi())

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
