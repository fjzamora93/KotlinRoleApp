package com.unir.sheet.data.repository

import android.util.Log
import androidx.room.util.query
import com.unir.sheet.data.local.dao.ItemDao
import com.unir.sheet.data.model.CharacterItemCrossRef
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.remote.model.ApiCharacterItem
import com.unir.sheet.data.model.CharacterItemDetail
import com.unir.sheet.data.remote.model.ApiItem
import com.unir.sheet.data.remote.model.toCharacterItemDetail
import com.unir.sheet.data.remote.service.ApiService
import com.unir.sheet.domain.repository.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        gameSessionId: Int
    ): Result<List<Item>> {
        return try {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.getItemsBySession(gameSessionId)
                    if (response.isSuccessful) {
                        val apiItems: List<ApiItem> = response.body() ?: emptyList()
                        val itemList: List<Item> = apiItems.map { it.toItemEntity() }
                        itemDao.insertAll(itemList)
                    } else {
                        throw Exception("Error en la respuesta: ${response.code()}")
                    }
                } catch (e: Exception) {
                    Log.e("API Error", "Fallo al obtener items de la API", e)
                }
            }

            val localItems = itemDao.getItemsBySession(gameSessionId)
            Result.success(localItems)
        } catch (e: Exception) {
            println("Error de otro tipo dentro del repositorio al obtener los datos de la API")
            Result.failure(e)
        }
    }

    override suspend fun getItemDetail(characterId: Long, itemId: Int): Result<CharacterItemDetail> {
        return try {
            Result.success(itemDao.getItemDetail(characterId, itemId))
        } catch (e : Exception) {
            Result.failure(e)
        }
    }


    /** MÉTODOS DE ACCESO AL INVENTARIO  */
    override suspend fun getItemsByCharacterId(characterId: Long): Result<List<CharacterItemDetail>> {
        return try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiService.getItemsByCharacterId(characterId)
                if (response.isSuccessful) {
                    val apiItems: List<ApiCharacterItem> = response.body() ?: throw Exception("Error en la respuesta: ${response.code()}")
                    val itemsDetail: List<CharacterItemDetail> = apiItems.map { it.toCharacterItemDetail() }
                    itemsDetail.forEach { itemDetail ->
                        itemDao.insertOrUpdateItemWithCharacter(itemDetail.item, characterId, itemDetail.quantity)
                    }
                } else {
                    throw Exception("Error en la respuesta: ${response.code()}")
                }
            }
            val itemsDetail = itemDao.getItemsDetailByCharacter(characterId)
            Log.w("ITEMS", itemsDetail.toString())
            Result.success(itemsDetail)
        } catch (e: Exception) {
            println("Error en el repositorio al obtener los datos de la API")
            Result.failure(e)
        }
    }


    override suspend fun deleteItemFromCharacter(characterId: Long, itemId: Int): Result<List<CharacterItemDetail>> {
        return try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiService.deleteItemFromCharacter(characterId, itemId)
                if (response.isSuccessful) {
                    val apiItems: List<ApiCharacterItem> = response.body() ?: throw Exception("Error en la respuesta: ${response.code()}")
                    val itemsDetail: List<CharacterItemDetail> = apiItems.map { it.toCharacterItemDetail() }
                    itemsDetail.forEach { itemDetail ->
                        itemDao.insertOrUpdateItemWithCharacter(itemDetail.item, characterId, itemDetail.quantity)
                    }
                } else {
                    throw Exception("Error en la respuesta: ${response.code()}")
                }
            }
            itemDao.deleteItemFromCharacter(CharacterItemCrossRef(characterId, itemId))
            val itemsDetail = itemDao.getItemsDetailByCharacter(characterId)
            Result.success(itemsDetail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun upsertItemToCharacter(
        characterId: Long,
        item: Item,
        quantity: Int
    ): Result<List<CharacterItemDetail>> {
        return try {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val apiItem = item.toApiItem()
                    val response =
                        apiService.addOrUpdateItemToCharacter(characterId, apiItem, quantity)
                    if (response.isSuccessful) {
                        val apiItems: List<ApiCharacterItem> = response.body() ?: emptyList()
                        val itemsDetail: List<CharacterItemDetail> = apiItems.map { it.toCharacterItemDetail() }
                        itemsDetail.forEach { itemDetail ->
                            itemDao.insertOrUpdateItemWithCharacter(itemDetail.item, characterId, itemDetail.quantity)
                        }
                    } else {
                        throw Exception("Error en la respuesta: ${response.code()}")
                    }
                } catch (e: Exception) {
                    Log.e("API Error", "Fallo al obtener items de la API", e)
                }
            }
            itemDao.insertOrUpdateItemWithCharacter(item, characterId, quantity)
            val itemsDetail = itemDao.getItemsDetailByCharacter(characterId)
            Result.success(itemsDetail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sellItem(characterId: Long, item: Item): Result<Unit> {
        return try{
            itemDao.sellItem(characterId, item.id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun buyItem(characterId: Long, item: Item): Result<Unit> {
        return try{
            itemDao.buyItem(characterId, item.id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    // Método sin uso en la vista, ya que devuelve el CrossRef (id item, idc character y quantity)
//    override suspend fun getCharacterItem(
//        characterId: Long,
//        itemId: Int
//    ): Result<CharacterItemCrossRef> {
//        return try {
//            val itemCharacter : CharacterItemCrossRef = itemDao.getCharacterItem(characterId, itemId)
//                ?: throw NoSuchElementException("No se encontró el CharacterItemCrossRef para characterId: $characterId y itemId: $itemId")
//            Result.success(itemCharacter)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

}
