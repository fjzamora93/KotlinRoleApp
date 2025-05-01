package com.unir.roleapp.character.data.service

import com.unir.roleapp.character.data.model.remote.ApiCharacterItem
import com.unir.roleapp.character.data.model.remote.ItemDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemApiService {


    @GET("items-template")
    suspend fun getAllItems(): Response<List<ItemDTO>>

    @GET("items/game-session/{id}")
    suspend fun getItemsBySession(
        @Path("id") gameSessionId: Int
    ): Response<List<ItemDTO>>

    @GET("custom-items/character/{id}")
    suspend fun getItemsByCharacterId(
        @Path("id") characterId: Long
    ): Response<List<ApiCharacterItem>>

    @POST("custom-items")
    suspend fun addOrUpdateItemToCharacter(
        @Query("characterId") characterId: Long,
        @Body customItemDTO: ItemDTO,
        @Query("quantity") quantity: Int
    ): Response<List<ApiCharacterItem>>

    @DELETE("custom-items")
    suspend fun deleteItemFromCharacter(
        @Query("characterId") characterId: Long,
        @Query("itemId") itemId: Int
    ): Response<List<ApiCharacterItem>>

    // Actualización de todas las relaciones entre un personaje y sus items
    @PUT("custom-items/sync")
    suspend fun updateItemsToCharacter(
        @Body characterItems : List <ApiCharacterItem>
    ) : Response<List<ApiCharacterItem>>

}