package com.unir.sheet.data.remote.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("items")
    suspend fun getItems(): Response<List<Map<String, Any>>>

    @GET("spells")
    suspend fun getSpells(): Response<List<Map<String, Any>>>

    @GET("spells/filter")
    suspend fun getSpellsByLevelAndRoleClass(
        @Query("level") level: Int,
        @Query("roleClass") roleClass: String
    ): Response<List<Map<String, Any>>>


    @GET("characters")
    suspend fun getCharacters(): Response<List<Map<String, Any>>>



    @GET("skills")
    suspend fun getSkills(): Response<List<Map<String, Any>>>
}
