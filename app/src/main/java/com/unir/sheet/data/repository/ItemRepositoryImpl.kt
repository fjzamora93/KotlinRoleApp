package com.unir.sheet.data.repository

import com.unir.sheet.data.local.dao.ItemDao
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.remote.model.ApiItem
import com.unir.sheet.data.remote.service.ApiService
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val itemDao: ItemDao
) : ItemRepository {

    // https://springbootroleplay-production.up.railway.app/api/items
    override suspend fun getAllItems(): Result<List<Item>> {
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
            println("Error de otro tipo dentro del repositorio al obtener los datos de la API")
            Result.failure(e)
        }
    }


    override suspend fun getFilteredItems(
        name: String?,
        category: String?,
        goldValue: Int?
    ): Result<List<Item>> {
        return try {
            val response = apiService.getFilteredItems(name, category, goldValue)
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



    /** MÉTODOS DE ACCESO AL INVENTARIO - LOCALES CON ROOM  */
    override suspend fun getItemsByCharacterId(characterId: Int): Result<List<Item>> {
        return try {
            val localItems = itemDao.getItemsByCharacterId(characterId)
            if (localItems != null) {
                Result.success(localItems)
            } else {
                Result.failure(Exception("Ítem sno encontrados"))
            }
        } catch (e: Exception) {
            Result.failure(e) // Capturamos cualquier error
        }
    }


    override suspend fun deleteItem(item: Item): Result<Unit> {
        return try {
            itemDao.deleteItem(item)
            Result.success(Unit) // Indica éxito
        } catch (e: Exception) {
            Result.failure(e) // Indica error con la excepción capturada
        }
    }

    override suspend fun insertOrUpdate(item: Item): Result<Unit> {
        return try {
            itemDao.insertOrUpdate(item)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



}
