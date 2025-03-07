package com.unir.sheet.data.repository

import com.unir.sheet.data.local.dao.ItemDao
import com.unir.sheet.data.model.CharacterItemCrossRef
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.remote.model.ApiCharacterItem
import com.unir.sheet.data.model.CharacterItemDetail
import com.unir.sheet.data.remote.model.ApiItem
import com.unir.sheet.data.remote.model.toCharacterItemDetail
import com.unir.sheet.data.remote.service.ApiService
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val itemDao: ItemDao
) : ItemRepository {

    // https://springbootroleplay-production.up.railway.app/api/items
    override suspend fun getTemplateItems(): Result<List<Item>> {
        return try {
            val response = apiService.getAllItems()
            if (response.isSuccessful) {
                val apiItems: List<ApiItem> = response.body() ?: emptyList()
                val itemList: List<Item> = apiItems.map { it.toItemEntity() }
                Result.success(itemList)
            } else {
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            println("Error de otro tipo dentro del repositorio al obtener los Items de la API")
            Result.failure(e)
        }
    }


    override suspend fun getItemsBySession(
        gameSessionId: Int): Result<List<Item>> {
        return try {
            val response = apiService.getItemsBySession(gameSessionId)
            println("Respuesta de la API: $response dentro de la SESIÓN $gameSessionId")
            if (response.isSuccessful){
                val apiItems: List<ApiItem> = response.body() ?: emptyList()
                val itemList: List<Item> = apiItems.map { it.toItemEntity() }
                Result.success(itemList)
            } else {
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            println("Error de otro tipo dentro del repositorio al obtener los datos de la API")
            Result.failure(e)
        }
    }



    /** MÉTODOS DE ACCESO AL INVENTARIO  */
    override suspend fun getItemsByCharacterId(characterId: Int): Result<List<CharacterItemDetail>> {
        return try {
            val response = apiService.getItemsByCharacterId(characterId)
            if (response.isSuccessful) {
                val apiItems: List<ApiCharacterItem> = response.body()
                    ?: return Result.failure(Exception("La respuesta de la API es nula"))

                println("CharacterItemList: $apiItems")

                val itemsDetail: List<CharacterItemDetail> = apiItems.map { it.toCharacterItemDetail() }
                Result.success(itemsDetail )
            } else {
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            println("Error en el repositorio al obtener los datos de la API")
            Result.failure(e)
        }
    }



    override suspend fun deleteItemById(characterId: Int, itemId: Int): Result<Unit> {
        return try {
            val response = apiService.deleteItemFromCharacter(characterId, itemId)

            if (response.isSuccessful) {
                Result.success(Unit) // Indica éxito
            } else {
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addItemToCharacter(
        characterId: Int,
        item: Item,
        quantity: Int
    ): Result<Unit> {
        return try {
            val apiItem = item.toApiItem()
            val response = apiService.addOrUpdateItemToCharacter(characterId, apiItem, quantity)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getCharacterItem(
        characterId: Int,
        itemId: Int
    ): Result<CharacterItemCrossRef> { // Cambia el tipo de retorno a Result<CharacterItemCrossRef> (sin el ?)
        return try {
            val itemCharacter = itemDao.getCharacterItem(characterId, itemId)
            if (itemCharacter != null) {
                Result.success(itemCharacter) // Retorna el resultado si existe
            } else {
                Result.failure(NoSuchElementException("No se encontró el CharacterItemCrossRef para characterId: $characterId y itemId: $itemId"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
