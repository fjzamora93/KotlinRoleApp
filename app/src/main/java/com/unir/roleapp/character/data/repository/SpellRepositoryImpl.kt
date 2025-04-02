package com.roleapp.character.data.repository

import com.roleapp.character.data.model.local.Spell
import com.roleapp.character.data.model.remote.SpellDTO
import com.roleapp.character.data.service.SpellApiService
import com.roleapp.character.domain.repository.SpellRepository
import javax.inject.Inject


class SpellRepositoryImpl @Inject constructor(
    private val apiService: SpellApiService
) : SpellRepository {
    override suspend fun getAllSpells(): Result<List<Spell>> {
        return try {
            val response = apiService.getAllSpells()
            if (response.isSuccessful) {
                val resultsList: List<SpellDTO>  = response.body() ?: emptyList()
                val spellList = resultsList.map({ it.toSpellEntity() })
                Result.success(spellList)
            } else {
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    override suspend fun getSpellsByLevelAndRoleClass(level: Int, roleClass: String): Result<List<Spell>> {
        return try {
            val response = apiService.getSpellsByLevelAndRoleClass(level, roleClass)
            if (response.isSuccessful) {
                val resultsResponse: List<SpellDTO> = response.body() ?: emptyList()
                val resultsList =  resultsResponse.map({ it.toSpellEntity() })
                Result.success(resultsList)
            } else {
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}