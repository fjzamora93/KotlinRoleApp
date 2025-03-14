package com.unir.auth.data.service

import com.unir.auth.data.model.LoginRequest
import com.unir.auth.data.model.LoginResponse
import com.unir.auth.data.model.RefreshTokenRequest
import com.unir.character.data.model.remote.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT


// TODO : AuthApiService está mezclando al Usuario con la autentificación. Separar competencias en dos servicios distintos.

interface AuthApiService {


    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/refresh-token")
    suspend fun refreshAccessToken(@Body request: RefreshTokenRequest): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun signup(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun logoutUser(@Header("Authorization") token: String): Response<Void>


}