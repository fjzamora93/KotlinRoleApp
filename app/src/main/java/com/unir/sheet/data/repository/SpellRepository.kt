package com.unir.sheet.data.repository

import com.unir.sheet.data.model.Spell
import com.unir.sheet.data.remote.service.ApiService
import javax.inject.Inject


class SpellRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchSpells(filter: String): Result<List<Spell>> {
        return try {
            val response = apiService.getSpells()
            if (response.isSuccessful) {
                val resultsResponse: List<Map<String, Any>> = response.body() ?: emptyList()
                val resultsList: List<Spell> = resultsResponse.mapNotNull { mapApiResultToLocal(it) }

                // Filtramos los resultados si es necesario
                val filteredItems = if (filter.isNotEmpty()) {
                    resultsList.filter {
                        val name = it.name as? String ?: ""
                        name.contains(filter, ignoreCase = true)
                    }
                } else {
                    resultsList
                }

                Result.success(filteredItems)
            } else {
                // Manejamos errores HTTP
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
                val resultsResponse: List<Map<String, Any>> = response.body() ?: emptyList()
                val resultsList: List<Spell> = resultsResponse.mapNotNull { mapApiResultToLocal(it) }
                Result.success(resultsList)
            } else {
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun mapApiResultToLocal(apiResult: Map<String, Any>) : Spell {
        return Spell(
            id = apiResult["id"] as? Int ?: 0,
            name = apiResult["name"] as? String ?: "",
            description = apiResult["description"] as? String ?: "",
            level = apiResult["level"] as? Int ?: 0,
            cost = apiResult["cost"] as? Int ?: 0,
            dice = apiResult["dice"] as? Int ?: 0,
            imgUrl = apiResult["imgUrl"] as? String ?: "",
        )
    }



}