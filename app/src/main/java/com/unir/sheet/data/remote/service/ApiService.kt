package com.unir.sheet.data.remote.service

import com.unir.sheet.data.model.CharacterWithItems
import com.unir.sheet.data.remote.model.ApiCharacterItem
import com.unir.sheet.data.remote.model.ApiCharacterRequest
import com.unir.sheet.data.remote.model.ApiCharacterResponse
import com.unir.sheet.data.remote.model.ApiGameSession
import com.unir.sheet.data.remote.model.ApiItem
import com.unir.sheet.data.remote.model.ApiSkill
import com.unir.sheet.data.remote.model.ApiSpell
import com.unir.sheet.data.remote.model.ApiUser
import com.unir.sheet.data.remote.model.LoginRequest
import com.unir.sheet.data.remote.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    // INICIAR SESIÓN

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun signup(@Body request: LoginRequest): Response<LoginResponse>

    @GET("user/me")
    suspend fun getUser(@Header("Authorization") token: String): Response<ApiUser>

    @POST("user/logout")
    suspend fun logoutUser(@Header("Authorization") token: String): Response<Void>

    @PUT("user/update")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body user: ApiUser
    ): Response<ApiUser>

    @DELETE("user/delete")
    suspend fun deleteUser(@Header("Authorization") token: String): Response<Void>



    // ITEMS
    @GET("items-template")
    suspend fun getAllItems(): Response<List<ApiItem>>

    @GET("items/game-session/{id}")
    suspend fun getItemsBySession(
        @Path("id") gameSessionId: Int
    ): Response<List<ApiItem>>

    @GET("custom-items/character/{id}")
    suspend fun getItemsByCharacterId(
        @Path("id") characterId: Int
    ): Response<List<ApiCharacterItem> >

    @POST("custom-items")
    suspend fun addOrUpdateItemToCharacter(
        @Query("characterId") characterId: Int,
        @Body customItemDTO: ApiItem,
        @Query("quantity") quantity: Int
    ): Response<ApiItem>

    @DELETE("custom-items")
    suspend fun deleteItemFromCharacter(
        @Query("characterId") characterId: Int,
        @Query("itemId") itemId: Int
    ): Response<Void>



    // SPELLS
    @GET("spells")
    suspend fun getAllSpells(): Response<List<ApiSpell>>

    @GET("spells/filter")
    suspend fun getSpellsByLevelAndRoleClass(
        @Query("level") level: Int,
        @Query("roleClass") roleClass: String
    ): Response<List<ApiSpell>>




    // CHARACTER

    // OBTENER LOS PERSONAJES DE UN USUARIO
    @GET("characters/user/{userId}")
    suspend fun getCharactersByUserId(
        @Path("userId") userId: Int
    ):  Response<List<ApiCharacterResponse>>

    // OBTENER UN PERSONAJE POR SU ID
    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ):  Response<ApiCharacterResponse>

    // CREAR O ACTUALIZAR UN PERSONAJE
    @POST("characters")
    suspend fun saveCharacter(
        @Body character: ApiCharacterRequest
    ):  Response<ApiCharacterResponse>

    // ELIMINAR UN PERSONAJE POR ID
    @DELETE("characters/{id}")
    suspend fun deleteCharacter(
        @Path("id") id: Int
    ): Response<Void>



    // SKILLS
    // OBTENER TODAS LAS HABILIDADES
    @GET("skills")
    suspend fun getAllSkills(): Response<List<ApiSkill>>

    @GET("skills/{characterId}")
    suspend fun getSkillsByCharacterId(@Path("id") id: Int): Response<List<ApiSkill>>

    // AÑADIR HABILIDAD A UN PERSONAJE
    @POST("skills")
    suspend fun addSkillToCharacter(
        @Query("characterId") characterId: Long,
        @Query("skillId") skillId: Long
    ): Response<ApiCharacterResponse>

    @POST("skills/addDefault/{characterId}")
    suspend fun addDefaultSkills(
        @Path("characterId") characterId: Int,
        @Body skillIds: List<Int>
    ): Response<ApiCharacterResponse>

    // ELIMINAR HABILIDAD DE UN PERSONAJE
    @DELETE("skills")
    suspend fun deleteSkillFromCharacter(
        @Query("characterId") characterId: Long,
        @Query("skillId") skillId: Long
    ): Response<ApiCharacterResponse>





    // BUSCAR SESIÓN POR ID
    @GET("gamesession/{id}")
    suspend fun getGameSessionById(@Path("id") id: Long): Response<ApiGameSession>

    // CREAR UNA NUEVA SESIÓN
    @POST("gamesession/create")
    suspend fun createGameSession(@Body gameSession: ApiGameSession): Response<ApiGameSession>

    // ELIMINAR SESIÓN
    @DELETE("gamesession/{id}")
    suspend fun deleteGameSession(@Path("id") id: Long): Response<Void>

    // AÑADIR PERSONAJE A LA SESIÓN
    @POST("gamesession/add-character")
    suspend fun addCharacterToGameSession(
        @Query("characterId") characterId: Long,
        @Query("gameSessionId") gameSessionId: Long
    ): Response<ApiCharacterResponse>

    // AÑADIR OBJETO A LA SESIÓN
    @POST("gamesession/add-item")
    suspend fun addItemToGameSession(
        @Query("itemId") itemId: Long,
        @Query("gameSessionId") gameSessionId: Long
    ): Response<ApiItem>

}
