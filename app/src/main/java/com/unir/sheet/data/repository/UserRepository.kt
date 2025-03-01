package com.unir.sheet.data.repository

import androidx.datastore.preferences.protobuf.Api
import com.unir.sheet.data.local.SessionManager
import com.unir.sheet.data.model.User
import com.unir.sheet.data.remote.model.ApiUser
import com.unir.sheet.data.remote.model.LoginRequest
import com.unir.sheet.data.remote.service.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService,
    private val sessionManager: SessionManager
) {
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                sessionManager.saveToken(loginResponse.token)

                Result.success(loginResponse.user.toUserEntity())
            } else {
                Result.failure(Exception("Error en login: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signup(email: String, password: String, confirmPassword: String): Result<User> {
        println("!! CORREGIR SIGNUP, ASEGURAR DE QUE FUNCIONA BIEN (CREO QUE EN LA API EL SIGNUP NO DEVUELVE UN TOKEN")
        return try {
            if (password != confirmPassword) {
                return Result.failure(Exception("Las contraseñas no coinciden"))
            }
            val newUserRequest = LoginRequest(email, password)
            val response = api.signup(newUserRequest)

            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                sessionManager.saveToken(loginResponse.token)
                Result.success(loginResponse.user.toUserEntity())
            } else {
                Result.failure(Exception("Error en login: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<Unit> {
        return try {
            val token = sessionManager.getToken() ?: return Result.failure(Exception("No hay token"))
            val response = api.logoutUser("Bearer $token")
            if (response.isSuccessful) {
                sessionManager.clearToken()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error en logout: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(): Result<User> {
        return try {
            val token = sessionManager.getToken() ?: return Result.failure(Exception("No hay token"))
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

    suspend fun updateUser(user: ApiUser): Result<User> {
        return try {
            val token = sessionManager.getToken() ?: return Result.failure(Exception("No hay token"))
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

    suspend fun deleteUser(): Result<Unit> {
        return try {
            val token = sessionManager.getToken() ?: return Result.failure(Exception("No hay token"))
            val response = api.deleteUser("Bearer $token")
            if (response.isSuccessful) {
                sessionManager.clearToken() // Borra el usuario de DataStore
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error eliminando usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
