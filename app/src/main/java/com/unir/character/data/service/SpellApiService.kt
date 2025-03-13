package com.unir.character.data.service

import com.unir.character.data.model.remote.SpellDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpellApiService {

    @GET("spells")
    suspend fun getAllSpells(): Response<List<SpellDTO>>

    @GET("spells/filter")
    suspend fun getSpellsByLevelAndRoleClass(
        @Query("level") level: Int,
        @Query("roleClass") roleClass: String
    ): Response<List<SpellDTO>>


}