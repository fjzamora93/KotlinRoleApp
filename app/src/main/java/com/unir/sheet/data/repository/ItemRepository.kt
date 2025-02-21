package com.unir.sheet.data.repository

import com.unir.sheet.data.model.Item
import com.unir.sheet.data.remote.service.ApiService
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val apiService: ApiService
) {

    // https://springbootroleplay-production.up.railway.app/api/items
    suspend fun fetchItems(): Result<List<Item>> {
        return try {
            val response = apiService.getItems()
            if (response.isSuccessful) {
                val itemResponse: List<Map<String, Any>> = response.body() ?: emptyList()
                val itemList: List<Item> = itemResponse.mapNotNull { mapApiItemToLocal(it) }
                println("Resultado dentro del Repository: $itemList")
                Result.success(itemList)
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
            id = apiItem["id"] as? String ?: "",
            name = apiItem["name"] as? String ?: "",
            imgUrl = apiItem["imgUrl"] as? String ?: "",
            description = apiItem["description"] as? String ?: "",
            dice = apiItem["dice"] as? Int ?: 1,
            statValue = apiItem["statValue"] as? Int ?: 0,
            statType = apiItem["statType"] as? String ?: "",
            category = apiItem["category"] as? String ?: "",
            goldValue  = apiItem["goldValue"] as? Int ?: 50,
        )
    }
}
