package com.unir.sheet.domain.repository

import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterItemDetail

interface ItemRepository {

    /** MÉTODOS PARA LA API REMOTA */
    suspend fun getTemplateItems(): Result<List<Item>>
    suspend fun getItemsBySession( gameSessionId: Int): Result<List<Item>>


    suspend fun deleteItemFromCharacter(characterId: Long, itemId: Int) :  Result<List<CharacterItemDetail>>

    suspend fun upsertItemToCharacter(characterId: Long, item: Item, quantity: Int) :  Result<List<CharacterItemDetail>>
    suspend fun getItemsByCharacterId(characterId: Long): Result<List<CharacterItemDetail>>

    suspend fun sellItem(characterId: Long, item: Item) :  Result<Unit>
    suspend fun buyItem(characterId: Long, item: Item) :  Result<Unit>

    suspend fun getItemDetail(characterId: Long, itemId: Int): Result<CharacterItemDetail>
}
