package com.roleapp.adventure.data.service

import com.roleapp.character.data.model.remote.CharacterResponse
import com.roleapp.character.data.model.remote.GameSessionDTO
import com.roleapp.character.data.model.remote.ItemDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


//! NO TENER EN CUENTA -> UTILIZAR LO DE PABLO

interface GameSessionApiService {
    // BUSCAR SESIÓN POR ID
    @GET("gamesession/{id}")
    suspend fun getGameSessionById(@Path("id") id: Long): Response<GameSessionDTO>

    // CREAR UNA NUEVA SESIÓN
    @POST("gamesession/create")
    suspend fun createGameSession(@Body gameSession: GameSessionDTO): Response<GameSessionDTO>

    // ELIMINAR SESIÓN
    @DELETE("gamesession/{id}")
    suspend fun deleteGameSession(@Path("id") id: Long): Response<Void>

    // AÑADIR PERSONAJE A LA SESIÓN
    @POST("gamesession/add-character")
    suspend fun addCharacterToGameSession(
        @Query("characterId") characterId: Long,
        @Query("gameSessionId") gameSessionId: Long
    ): Response<CharacterResponse>

    // AÑADIR OBJETO A LA SESIÓN
    @POST("gamesession/add-item")
    suspend fun addItemToGameSession(
        @Query("itemId") itemId: Long,
        @Query("gameSessionId") gameSessionId: Long
    ): Response<ItemDTO>
}