package com.unir.sheet.domain.repository

import com.unir.sheet.data.model.Item

interface ItemRepository {

    /** MÉTODOS PARA LA API REMOTA */
    suspend fun getAllItems(): Result<List<Item>>
    suspend fun getFilteredItems(name: String?, category: String?, goldValue: Int?): Result<List<Item>>


    /** MÉTODOS LOCALES DEL ITEM */
    suspend fun deleteItem(item: Item) :  Result<Unit>
    suspend fun insertOrUpdate(item: Item) :  Result<Unit>
    suspend fun getItemsByCharacterId(characterId: Int): Result<List<Item>>


}
