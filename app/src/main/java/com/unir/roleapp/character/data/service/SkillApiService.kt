package com.unir.roleapp.character.data.service

import com.unir.roleapp.character.data.model.local.Skill
import com.unir.roleapp.character.data.model.remote.SkillDTO
import com.unir.roleapp.character.data.model.remote.CharacterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SkillApiService {

    @GET("skills")
    suspend fun getAllSkills(): Response<List<SkillDTO>>

    @GET("skills/{characterId}")
    suspend fun getSkillsByCharacterId(@Path("id") id: Long): Response<List<SkillDTO>>

    // AÑADIR HABILIDAD A UN PERSONAJE
    @POST("skills")
    suspend fun addSkillToCharacter(
        @Query("characterId") characterId: Long,
        @Query("skillId") skillId: Long
    ): Response<CharacterResponse>

    @POST("skills/addDefault/{characterId}")
    suspend fun addDefaultSkills(
        @Path("characterId") characterId: Long,
        @Body skillIds: List<Int>
    ): Response<List<Skill>>

    // ELIMINAR HABILIDAD DE UN PERSONAJE
    @DELETE("skills")
    suspend fun deleteSkillFromCharacter(
        @Query("characterId") characterId: Long,
        @Query("skillId") skillId: Long
    ): Response<CharacterResponse>



}