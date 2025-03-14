package com.unir.character.data.repository

import android.util.Log
import com.unir.character.data.dao.ItemDao
import com.unir.character.data.model.local.CharacterItemCrossRef
import com.unir.character.data.model.local.Item
import com.unir.character.data.model.remote.ApiCharacterItem
import com.unir.character.data.model.local.CharacterItemDetail
import com.unir.character.data.model.remote.ItemDTO
import com.unir.character.data.model.remote.toCharacterItemDetail
import com.unir.character.data.service.ItemApiService
import com.unir.character.domain.repository.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject



// TODO: ARREGLAR BUG QUE HAY AL OBTENER LOS ITEMS DE UN PERSONAJE. CUANDO SE ELIMINA LA BASE DE DATOS LOCAL, YA NO DEJA RECUPERAR LOS OBJETOS QUE HAY EN EL REMOTO
// Realizar pruebas con postman para añadir objetos a un personaje y después ver si se sincronizan bien al llegar a la APlicación.

class ItemRepositoryImpl @Inject constructor(
    private val apiService: ItemApiService,
    private val itemDao: ItemDao
) : ItemRepository {

    // https://springbootroleplay-production.up.railway.app/api/items
    override suspend fun getTemplateItems(): Result<List<Item>> {
        return try {
            val response = apiService.getAllItems()
            if (response.isSuccessful) {
                val itemDTOS: List<ItemDTO> = response.body() ?: emptyList()
                val itemList: List<Item> = itemDTOS.map { it.toItemEntity() }
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
                        val itemDTOS: List<ItemDTO> = response.body() ?: emptyList()
                        val itemList: List<Item> = itemDTOS.map { it.toItemEntity() }
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
                syncToApiCharacterItem(characterId)
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
            itemDao.deleteItemFromCharacter(CharacterItemCrossRef(characterId, itemId))
            val itemsDetail = itemDao.getItemsDetailByCharacter(characterId)
            Result.success(itemsDetail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Al llamar a este método el updatedAT se fija automáticamente.
    override suspend fun upsertItemToCharacter(
        characterId: Long,
        item: Item,
        quantity: Int,
    ): Result<List<CharacterItemDetail>> {
        return try {
            val itemsDetail = itemDao.insertOrUpdateItemWithCharacter(item, characterId, quantity)
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


    /**
     * Sincroniza la base de datos local y la remota.
     *
     * SI la base de datos remota está más actualizada, devolverá una lista actualizada.
     *
     * Si la versión más actual es la local, devolverá exactamente lo mismo que se envió.
     *
     * */
    override suspend fun syncToApiCharacterItem(
        characterId: Long,
    ): Result<List<CharacterItemDetail>> {
        return try {
            // 1. Obtener los detalles locales del ítem
            val localItemsDetails: List<CharacterItemDetail> = itemDao.getItemsDetailByCharacter(characterId)

            // 2. Sincronizar con la API
            val response = apiService.updateItemsToCharacter(localItemsDetails.map {  it.toCharacterApiCharacterItem() })

            // 3. Verificar si la respuesta fue exitosa
            if (!response.isSuccessful) {
                throw Exception("Error en la respuesta: ${response.code()}")
            }

            // 4. Obtener los datos actualizados de la API
            val apiItems: List<ApiCharacterItem> = response.body()
                ?: throw Exception("Error en el cuerpo de la respuesta")

            // 5. Convertir los datos de la API a dominio local
            val updatedItemsDetail: List<CharacterItemDetail> = apiItems.map { it.toCharacterItemDetail() }


            // 6. Comparar los datos locales con los datos de la API y actualizar SOLO si hay cambios
            if (localItemsDetails != updatedItemsDetail) {

                // Encontrar las relaciones locales que no están en la API y borrarlas localmente
                val localItemIds = localItemsDetails.map { it.item.id }.toSet()
                val apiItemIds = updatedItemsDetail.map { it.item.id }.toSet()
                val itemsToDelete = localItemIds - apiItemIds
                itemsToDelete.forEach { itemId ->
                    itemDao.deleteItemFromCharacter(CharacterItemCrossRef(characterId, itemId))
                }

                // Actualizar las relaciones que permanecen
                updatedItemsDetail.forEach { itemDetail ->
                    itemDao.insertOrUpdateItemWithCharacter(
                        itemDetail.item,
                        characterId,
                        itemDetail.quantity
                    )
                }
            }

            // 7. Devolver la lista sincronizada
            Result.success(updatedItemsDetail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
