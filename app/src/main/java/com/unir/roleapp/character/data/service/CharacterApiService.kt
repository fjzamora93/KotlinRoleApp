package com.roleapp.character.data.service

import com.roleapp.character.data.model.remote.CharacterRequest
import com.roleapp.character.data.model.remote.CharacterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CharacterApiService {
    @GET("characters/user/{userId}")
    suspend fun getCharactersByUserId(
        @Path("userId") userId: Int
    ): Response<List<CharacterResponse>>

    // OBTENER UN PERSONAJE POR SU ID
    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Long
    ): Response<CharacterResponse>

    // CREAR O ACTUALIZAR UN PERSONAJE
    @POST("characters")
    suspend fun saveCharacter(
        @Body character: CharacterRequest
    ): Response<CharacterResponse>

    // ELIMINAR UN PERSONAJE POR ID
    @DELETE("characters/{id}")
    suspend fun deleteCharacter(
        @Path("id") id: Long
    ): Response<Void>

}