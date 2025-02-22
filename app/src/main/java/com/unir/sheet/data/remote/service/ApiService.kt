package com.unir.sheet.data.remote.service

import com.unir.sheet.data.remote.model.ApiItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    // ITEMS
    @GET("items")
    suspend fun getItems(): Response<List<ApiItem>>

    // SPELLS
    @GET("spells")
    suspend fun getSpells(): Response<List<Map<String, Any>>>

    @GET("spells/filter")
    suspend fun getSpellsByLevelAndRoleClass(
        @Query("level") level: Int,
        @Query("roleClass") roleClass: String
    ): Response<List<Map<String, Any>>>

    // CHARACTER
    @GET("characters")
    suspend fun getCharacters(): Response<List<Map<String, Any>>>

    // SKILLS
    @GET("skills")
    suspend fun getSkills(): Response<List<Map<String, Any>>>
}
