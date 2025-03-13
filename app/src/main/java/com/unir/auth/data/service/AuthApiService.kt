package com.unir.auth.data.service

import com.unir.auth.data.model.LoginRequest
import com.unir.auth.data.model.LoginResponse
import com.unir.character.data.model.remote.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApiService {

    // ENDPOINTS AUTH

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("user/refresh-token")
    suspend fun refreshAccessToken(@Body request: String): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun signup(@Body request: LoginRequest): Response<LoginResponse>

    @POST("user/logout")
    suspend fun logoutUser(@Header("Authorization") token: String): Response<Void>



    // ENDPOINTS USER

    @GET("user/me")
    suspend fun getUser(@Header("Authorization") token: String): Response<UserDTO>

    @PUT("user/update")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body user: UserDTO
    ): Response<UserDTO>

    @DELETE("user/delete")
    suspend fun deleteUser(@Header("Authorization") token: String): Response<Void>

}