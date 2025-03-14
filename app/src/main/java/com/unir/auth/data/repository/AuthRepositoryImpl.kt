package com.unir.auth.data.repository

import com.unir.auth.data.service.AuthApiService
import com.unir.auth.security.TokenManager
import com.unir.auth.data.model.User
import com.unir.character.data.model.remote.UserDTO
import com.unir.auth.data.model.LoginRequest
import com.unir.auth.data.model.RefreshTokenRequest
import com.unir.auth.domain.repository.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService,
    private val tokenManager: TokenManager
) : AuthRepository {


    override suspend fun login(email: String, password: String): Result<User> {
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



    // TODO: Mejorar el Sign UP para que haga un autologin (coordinarlo con el Backend)
    // TODO: IMplementar el Doble Factor  al registrase (o mandar un email o algo)
    override suspend fun signup(email: String, password: String): Result<User> {
        return try {

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

    // TODO: Aunque en el front se eliminan los tokens en el logout (aquí ya no hay que hacer nada),
    //  es necesario reforzar la seguridad en el backend (crear lista negra)
    override suspend fun logout(): Result<Unit> {
        return try {
            val response = api.logoutUser()

            if (response.isSuccessful) {
                tokenManager.clearTokens()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error en logout: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    // Método de autologin (condicionado al Use Case que va a forzar este método al crear el Screen)
    override suspend fun autoLogin(): Result<User> {
        return try {
            // Recuperar el refresh token almacenado
            val refreshToken = tokenManager.getRefreshToken()
            if (refreshToken == null) {
                return Result.failure(Exception("No hay refresh token almacenado"))
            }

            // Solicitar un nuevo access token usando el refresh token
            val response = api.refreshAccessToken(RefreshTokenRequest(refreshToken))
            if (response.isSuccessful && response.body() != null) {
                val refreshResponse = response.body()!!

                // Guardar el nuevo access token
                tokenManager.saveAccessToken(refreshResponse.accessToken)

                // Devolver el usuario autenticado
                Result.success(refreshResponse.user.toUserEntity())
            } else {
                Result.failure(Exception("Error en autologin. Introduce email y contraseña: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


