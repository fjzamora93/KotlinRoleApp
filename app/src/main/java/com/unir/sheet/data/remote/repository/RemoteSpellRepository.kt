package com.unir.sheet.data.remote.repository

import com.unir.sheet.data.local.model.Spell
import com.unir.sheet.data.remote.model.ApiResponse
import com.unir.sheet.data.remote.service.ApiService
import javax.inject.Inject


class RemoteSpellRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchSpells(filter: String): Result<List<Spell>> {
        return try {
            val response = apiService.getSpells()
            if (response.isSuccessful) {
                val apiResponse: ApiResponse? = response.body()
                val resultsResponse: List<Map<String, Any>> = apiResponse?.results ?: emptyList()

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


    suspend fun fetchSpellsByLevel(level: Int) : Result<List<Spell>> {
        return try {
            val response = apiService.getSpellsByLevel(level)
            if (response.isSuccessful) {
                val apiResponse: ApiResponse? = response.body()
                val resultsResponse: List<Map<String, Any>> = apiResponse?.results ?: emptyList()
                val resultsList: List<Spell> = resultsResponse.mapNotNull { mapApiResultToLocal(it) }
                Result.success(resultsList)
            } else {
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun mapApiResultToLocal(apiResult: Map<String, Any>) : Spell{
        return Spell(
            id = apiResult["key"] as? String ?: "",
            name = apiResult["name"] as? String ?: "",
            desc = apiResult["desc"] as? String ?: "",
            level = apiResult["level"] as? Int ?: 0,
            targetCount = apiResult["target_count"] as? Int ?: 0,
            range = apiResult["range"] as? Double ?: 0.0,
        )
    }



}