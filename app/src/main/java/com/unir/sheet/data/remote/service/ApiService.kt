package com.unir.sheet.data.remote.service

import com.unir.sheet.data.remote.model.ApiCharacter
import com.unir.sheet.data.remote.model.ApiGameSession
import com.unir.sheet.data.remote.model.ApiItem
import com.unir.sheet.data.remote.model.ApiSkill
import com.unir.sheet.data.remote.model.ApiSpell
import com.unir.sheet.data.remote.model.ApiUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    // ITEMS
    // OBTENER TODOS LOS OBJETOS
    @GET("items")
    suspend fun getAllItems(): Response<List<ApiItem>>


    // FILTRO COMBINADO
    @GET("items/filter")
    suspend fun getFilteredItems(
        @Query("name") name: String?,
        @Query("category") category: String?,
        @Query("goldValue") goldValue: Int?
    ): Response<List<ApiItem>>



    // SPELLS
    @GET("spells")
    suspend fun getAllSpells(): Response<List<ApiSpell>>

    @GET("spells/filter")
    suspend fun getSpellsByLevelAndRoleClass(
        @Query("level") level: Int,
        @Query("roleClass") roleClass: String
    ): Response<List<ApiSpell>>




    // CHARACTER
    // OBTENER TODOS LOS PERSONAJES
    @GET("characters")
    suspend fun getAllCharacters(): Response<List<ApiCharacter>>

    // OBTENER LOS PERSONAJES DE UN USUARIO
    @GET("characters/user/{userId}")
    suspend fun getCharactersByUser(
        @Path("userId") userId: Long
    ):  Response<List<ApiCharacter>>

    // OBTENER UN PERSONAJE POR SU ID
    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Long
    ):  Response<List<ApiCharacter>>

    // CREAR O ACTUALIZAR UN PERSONAJE
    @POST("characters")
    suspend fun postCharacter(
        @Body character: ApiCharacter
    ):  Response<List<ApiCharacter>>

    // ELIMINAR UN PERSONAJE POR ID
    @DELETE("characters/{id}")
    suspend fun deleteCharacter(
        @Path("id") id: Long
    ): Response<Void>



    // SKILLS
    // OBTENER TODAS LAS HABILIDADES
    @GET("skills")
    suspend fun getAllSkills(): Response<List<ApiSkill>>

    // AÑADIR HABILIDAD A UN PERSONAJE
    @POST("skills")
    suspend fun addSkillToCharacter(
        @Query("characterId") characterId: Long,
        @Query("skillId") skillId: Long
    ): Response<ApiCharacter>

    // ELIMINAR HABILIDAD DE UN PERSONAJE
    @DELETE("skills")
    suspend fun deleteSkillFromCharacter(
        @Query("characterId") characterId: Long,
        @Query("skillId") skillId: Long
    ): Response<ApiCharacter>



    // BUSCAR USUARIO POR ID
    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<ApiUser>

    // INICIAR SESIÓN
    @GET("user/login")
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<ApiUser>

    // AÑADIR UN NUEVO USUARIO
    @POST("user/signup")
    suspend fun signUpUser(@Body user: ApiUser): Response<ApiUser>

    // ACTUALIZAR USUARIO
    @PUT("user/update")
    suspend fun updateUser(@Body user: ApiUser): Response<ApiUser>

    // ELIMINAR CUENTA
    @DELETE("user/{id}")
    suspend fun deleteUser(@Path("id") id: Long): Response<Void>






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
    ): Response<ApiCharacter>

    // AÑADIR OBJETO A LA SESIÓN
    @POST("gamesession/add-item")
    suspend fun addItemToGameSession(
        @Query("itemId") itemId: Long,
        @Query("gameSessionId") gameSessionId: Long
    ): Response<ApiItem>

}
