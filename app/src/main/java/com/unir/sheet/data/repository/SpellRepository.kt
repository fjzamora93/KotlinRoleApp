package com.unir.sheet.data.repository

import com.unir.sheet.data.model.Spell
import com.unir.sheet.data.remote.model.ApiSpell
import com.unir.sheet.data.remote.service.ApiService
import javax.inject.Inject


class SpellRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun fetchSpells(): Result<List<Spell>> {
        return try {
            val response = apiService.getAllSpells()
            if (response.isSuccessful) {
                val resultsList: List<ApiSpell>  = response.body() ?: emptyList()
                val spellList = resultsList.map({ it.toSpellEntity() })
                Result.success(spellList)
            } else {
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    suspend fun fetchSpellsByLevelAndRoleClass(level: Int, roleClass: String): Result<List<Spell>> {
        return try {
            val response = apiService.getSpellsByLevelAndRoleClass(level, roleClass)
            if (response.isSuccessful) {
                val resultsResponse: List<ApiSpell> = response.body() ?: emptyList()
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