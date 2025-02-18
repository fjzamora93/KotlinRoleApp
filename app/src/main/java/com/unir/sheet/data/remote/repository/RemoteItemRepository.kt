package com.unir.sheet.data.remote.repository


import com.unir.sheet.data.local.model.Item
import com.unir.sheet.data.remote.model.ApiResponse
import com.unir.sheet.data.remote.service.ApiService
import javax.inject.Inject



class RemoteItemRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchItems(filter: String): Result<List<Item>> {
        return try {
            val response = apiService.getItems()
            if (response.isSuccessful) {
                val apiResponse: ApiResponse? = response.body()
                val itemResponse: List<Map<String, Any>> = apiResponse?.results ?: emptyList()

                val itemList: List<Item> = itemResponse.mapNotNull { mapApiItemToLocal(it) }

                // Filtramos los resultados si es necesario
                val filteredItems = if (filter.isNotEmpty()) {
                    itemList.filter {
                        val name = it.name as? String ?: ""
                        name.contains(filter, ignoreCase = true)
                    }
                } else {
                    itemList
                }

                Result.success(filteredItems)
            } else {
                // Manejamos errores HTTP
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            // Manejamos excepciones de red u otras
            Result.failure(e)
        }
    }

    // Función para mapear el modelo de la API (ApiItemResponse) al modelo local (Item)
    private fun mapApiItemToLocal(apiItem: Map<String, Any>): Item {
        return Item(
            id = apiItem["key"] as? String ?: "",
            url = apiItem["url"] as? String ?: "",
            rangedAttackPossible = apiItem["ranged_attack_possible"] as? Boolean ?: false,
            name = apiItem["name"] as? String ?: "",
            damageDice = apiItem["damage_dice"] as? String ?: "",
            range = (apiItem["range"] as? Number)?.toFloat() ?: 0f,
            document = apiItem["document"] as? String ?: "",
            damageType = apiItem["damage_type"] as? String ?: "",
            goldValue = 10
        )
    }


}