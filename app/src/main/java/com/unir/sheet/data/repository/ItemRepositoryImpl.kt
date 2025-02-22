package com.unir.sheet.data.repository

import com.unir.sheet.data.local.dao.ItemDao
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.remote.service.ApiService
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val itemDao: ItemDao
) : ItemRepository {

    // https://springbootroleplay-production.up.railway.app/api/items
    override suspend fun fetchItems(): Result<List<Item>> {
        return try {
            val response = apiService.getItems()
            if (response.isSuccessful) {
                val itemResponse: List<Map<String, Any>> = response.body() ?: emptyList()
                println("Resultado dentro de la response: $itemResponse")

                val itemList: List<Item> = itemResponse.mapNotNull { mapApiItemToLocal(it) }
                Result.success(itemList)
            } else {
                // Manejamos errores HTTP
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            println("Error de otro tipo dentro del repositorio al obtener los datos de la API")
            Result.failure(e)
        }
    }

    override suspend fun getItemsByCharacterId(characterId: Int): Result<List<Item>> {
        return try {
            // Primero intentamos obtenerlo de la base de datos local
            val localItems = itemDao.getItemByCharacterId(characterId)
            if (localItems != null) {
                return Result.success(localItems)
            }
            Result.failure(Exception("Ítem sno encontrados"))
        } catch (e: Exception) {
            Result.failure(e) // Capturamos cualquier error
        }
    }

    override suspend fun getItemByCharacterIdAndItemId(characterId: Int, itemId: Int): Result<Item> {
        return try {
            // Primero intentamos obtenerlo de la base de datos local
            val localItem = itemDao.getItemByCharacterIdAndItemId(characterId, itemId)
            if (localItem != null) {
                return Result.success(localItem)
            }

//            // Si no está en la DB, lo buscamos en la API remota
//            val response = apiService.getItem(itemId)
//            if (response.isSuccessful) {
//                val item = response.body()?.let { mapApiItemToLocal(it) }
//
//                if (item != null) {
//                    itemDao.insertItem(item) // Guardamos en la DB para futuras consultas
//                    return Result.success(item)
//                }
//            }

            Result.failure(Exception("Ítem no encontrado ni en la DB ni en la API"))
        } catch (e: Exception) {
            Result.failure(e) // Capturamos cualquier error
        }
    }


    override suspend fun deleteItem(item: Item) {
        itemDao.deleteItem(item)
    }

    override suspend fun insertOrUpdate(item: Item) {
        itemDao.insertOrUpdate(item)
    }



    // Función para mapear el modelo de la API (ApiItemResponse) al modelo local (Item)
    private fun mapApiItemToLocal(apiItem: Map<String, Any>): Item {
        return Item(
            id = (apiItem["id"] as? Double)?.toInt() ?: 0,  // Convertir Double a Int
            characterId = (apiItem["characterId"] as? Double)?.toInt(),  // Convertir Double a Int si está presente
            gameSession = (apiItem["gameSession"] as? Double)?.toInt(),  // Convertir Double a Int si está presente

            name = apiItem["name"] as? String ?: "",
            description = apiItem["description"] as? String ?: "",
            imgUrl = apiItem["imgUrl"] as? String ?: "",
            goldValue = (apiItem["goldValue"] as? Double)?.toInt() ?: 50,  // Convertir Double a Int
            category = apiItem["category"] as? String ?: "",
            dice = (apiItem["dice"] as? Double)?.toInt() ?: 1,  // Convertir Double a Int

            quantity = (apiItem["quantity"] as? Double)?.toInt() ?: 1,  // Convertir Double a Int
            statValue = (apiItem["statValue"] as? Double)?.toInt() ?: 0,  // Convertir Double a Int
            statType = apiItem["statType"] as? String ?: ""
        )
    }

}
