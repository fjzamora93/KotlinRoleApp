package com.unir.auth.data.repository

import com.unir.auth.data.security.TokenManager
import com.unir.sheet.data.model.User
import com.unir.sheet.data.remote.model.ApiUser
import com.unir.auth.data.model.LoginRequest
import com.unir.sheet.data.remote.service.ApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!

                // Guardar tokens
                tokenManager.saveAccessToken(loginResponse.accessToken)
                tokenManager.saveRefreshToken(loginResponse.refreshToken)

                Result.success(loginResponse.user.toUserEntity())
            } else {
                Result.failure(Exception("Error en login: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Registro
    suspend fun signup(email: String, password: String, confirmPassword: String): Result<User> {
        return try {
            if (password != confirmPassword) {
                return Result.failure(Exception("Las contraseñas no coinciden"))
            }
            val newUserRequest = LoginRequest(email, password)
            val response = api.signup(newUserRequest)

            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!

                // Guardar tokens
                tokenManager.saveAccessToken(loginResponse.accessToken)
                tokenManager.saveRefreshToken(loginResponse.refreshToken)

                Result.success(loginResponse.user.toUserEntity())
            } else {
                Result.failure(Exception("Error en signup: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Logout
    suspend fun logout(): Result<Unit> {
        return try {
            val token = tokenManager.getAccessToken() ?: return Result.failure(Exception("No hay Access Token"))
            val response = api.logoutUser("Bearer $token")

            if (response.isSuccessful) {
                tokenManager.clearTokens() // Borra ambos tokens
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error en logout: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Obtener usuario (usando Access Token)
    suspend fun getUser(): Result<User> {
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
    suspend fun updateUser(user: ApiUser): Result<User> {
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
    suspend fun deleteUser(): Result<Unit> {
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

    // Renueva el Access Token usando el Refresh Token
    suspend fun refreshAccessToken(): Result<Boolean> {
        return try {
            val refreshToken = tokenManager.getRefreshToken() ?: return Result.failure(Exception("No hay Refresh Token"))

            val response = api.refreshAccessToken(refreshToken) // Llamada API para obtener nuevo Access Token

            if (response.isSuccessful && response.body() != null) {
                val newAccessToken = response.body()!!.accessToken
                tokenManager.saveAccessToken(newAccessToken) // Guardar el nuevo Access Token
                Result.success(true)
            } else {
                Result.failure(Exception("Error renovando Access Token"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


