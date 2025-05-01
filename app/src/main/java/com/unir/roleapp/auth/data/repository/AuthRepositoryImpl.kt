package com.unir.roleapp.auth.data.repository

import android.util.Log
import com.unir.roleapp.auth.data.dao.UserDao
import com.unir.roleapp.auth.data.service.AuthApiService
import com.unir.roleapp.auth.security.TokenManager
import com.unir.roleapp.auth.data.model.User
import com.unir.roleapp.auth.data.model.LoginRequest
import com.unir.roleapp.auth.data.model.RefreshTokenRequest
import com.unir.roleapp.auth.domain.repository.AuthRepository
import com.unir.roleapp.adventure.data.service.UserPreferences
import java.io.IOException
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService,
    private val userDao: UserDao,
    private val tokenManager: TokenManager,
    private val userPreferences: UserPreferences
) : AuthRepository {


    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.code() == 200 && response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!

                // Guardar tokens
                tokenManager.saveAccessToken(loginResponse.accessToken)
                tokenManager.saveRefreshToken(loginResponse.refreshToken)
                userPreferences.saveEmail(email)

                Log.e("AuthRepositoryImpl", "EMAIL DEL USUARIO ACTUAL: ${userPreferences.getEmail()}")


                val onlineUser = loginResponse.user.toUserEntity()
                userDao.upsertUser(onlineUser)

                Result.success(onlineUser)
            } else {
                val errorBody = response.errorBody()?.string()
                Result.failure(Exception("${errorBody}"))
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
                val errorBody = response.errorBody()?.string()
                Result.failure(Exception("$errorBody"))
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
                userPreferences.clear()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error en logout: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    /** Método de autologin. A diferenica del login normal, el autologin no necesita enviar a la API ni el email ni la contraseña.
     * Para completar la autentificación, le basta con enviar un RefreshToken y solicitar un nuevo AccessTOken.
     * */
    override suspend fun autoLogin(): Result<User> {
        return try {
            // Recuperar el refresh token almacenado
            val refreshToken = tokenManager.getRefreshToken()
                ?: return Result.failure(Exception("No hay refresh token almacenado"))

            // Solicitar un nuevo access token usando el refresh token
            val response = api.refreshAccessToken(RefreshTokenRequest(refreshToken))
            if (response.isSuccessful && response.body() != null) {
                val refreshResponse = response.body()!!

                // Guardar el nuevo access token
                tokenManager.saveAccessToken(refreshResponse.accessToken)
                val onlineUser = refreshResponse.user.toUserEntity()

                //TODO: Cambia la forma de almacenar para que no se rompan las relaciones
                userDao.upsertUser(onlineUser)
                Result.success(onlineUser)

            // Antes de lanzar el error, capturamos exactamente por qué no hay conexión
            } else {
                val errorMessage = when (response.code()) {
                    401 -> "Refresh token expired" // Or "Unauthorized"
                    400 -> "Invalid refresh token" // Or "Bad Request"
                    500 -> "Server error" // Or "Internal Server Error"
                    else -> "Network error: ${response.message()}"
                }
                Log.e("AuthRepositoryImpl", "Error en autologin: $errorMessage")
                throw IOException("Error en autologin: $errorMessage")
            }
        } catch (e: IOException) {
            val offlineUser = userDao.getUser()
            if (offlineUser != null) {
                Result.success(offlineUser)
            } else {
                Result.failure(Exception("Error de conexión. No hay datos locales disponibles."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}


