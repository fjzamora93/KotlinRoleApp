package com.unir.roleapp.auth.data.service

import com.unir.roleapp.auth.data.model.LoginRequest
import com.unir.roleapp.auth.data.model.LoginResponse
import com.unir.roleapp.auth.data.model.RefreshTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


// TODO : AuthApiService está mezclando al Usuario con la autentificación. Separar competencias en dos servicios distintos.

interface AuthApiService {


    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/refresh-token")
    suspend fun refreshAccessToken(@Body request: RefreshTokenRequest): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun signup(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun logoutUser(): Response<Void>


}