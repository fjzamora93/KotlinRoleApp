package com.unir.sheet.domain.repository

import com.unir.sheet.data.model.CharacterItemCrossRef
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterItemDetail

interface ItemRepository {

    /** MÉTODOS PARA LA API REMOTA */
    suspend fun getTemplateItems(): Result<List<Item>>
    suspend fun getItemsBySession( gameSessionId: Int): Result<List<Item>>


    suspend fun deleteItemById(characterId: Long, itemId: Int) :  Result<Unit>
    suspend fun addItemToCharacter(characteriId: Long, item: Item, quantity: Int) :  Result<Unit>
    suspend fun getItemsByCharacterId(characterId: Long): Result<List<CharacterItemDetail>>

    suspend fun getCharacterItem(characterId: Long, itemId: Int) :  Result<CharacterItemCrossRef?>

}
