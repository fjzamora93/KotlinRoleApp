package com.unir.sheet.data.repository

import com.unir.sheet.data.local.UserPreferences
import com.unir.sheet.data.model.User
import com.unir.sheet.data.remote.model.ApiUser
import com.unir.sheet.data.remote.model.LoginRequest
import com.unir.sheet.data.remote.service.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService,
    private val preferences: UserPreferences
) {
    suspend fun login(email: String, password: String): Result<ApiUser> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                preferences.saveUser(loginResponse.token, loginResponse.user.id.toString()) // Guarda en DataStore
                Result.success(loginResponse.user)
            } else {
                Result.failure(Exception("Error en login: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<Unit> {
        return try {
            val token = preferences.getToken() ?: return Result.failure(Exception("No hay token"))
            val response = api.logoutUser("Bearer $token")
            if (response.isSuccessful) {
                preferences.clearUser() // Borra token del almacenamiento
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error en logout: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(): Result<ApiUser> {
        return try {
            val token = preferences.getToken() ?: return Result.failure(Exception("No hay token"))
            val response = api.getUser("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error obteniendo usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUser(user: ApiUser): Result<ApiUser> {
        return try {
            val token = preferences.getToken() ?: return Result.failure(Exception("No hay token"))
            val response = api.updateUser("Bearer $token", user)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error actualizando usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteUser(): Result<Unit> {
        return try {
            val token = preferences.getToken() ?: return Result.failure(Exception("No hay token"))
            val response = api.deleteUser("Bearer $token")
            if (response.isSuccessful) {
                preferences.clearUser() // Borra el usuario de DataStore
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error eliminando usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
