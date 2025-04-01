package com.roleapp.auth.data.service

import com.roleapp.auth.data.model.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface UserApiService {

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