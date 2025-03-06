package com.unir.sheet.domain.repository

import com.unir.sheet.data.model.CharacterItemCrossRef
import com.unir.sheet.data.model.Item

interface ItemRepository {

    /** MÉTODOS PARA LA API REMOTA */
    suspend fun getAllItems(): Result<List<Item>>
    suspend fun getItemsBySession( gameSessionId: Int): Result<List<Item>>


    suspend fun deleteItemById(characterId: Int, itemId: Int) :  Result<Unit>
    suspend fun insertOrUpdate(characteriId: Int, item: Item, quantity: Int) :  Result<Unit>
    suspend fun getItemsByCharacterId(characterId: Int): Result<List<Item>>

    suspend fun getCharacterItem(characterId: Int, itemId: Int) :  Result<CharacterItemCrossRef?>

}
