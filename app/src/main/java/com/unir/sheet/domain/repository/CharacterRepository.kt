package com.unir.sheet.domain.repository

import com.unir.sheet.data.local.database.RolCharacterWithAllRelations
import com.unir.sheet.data.local.model.Item
import com.unir.sheet.data.local.model.RolCharacter

interface CharacterRepository {
    suspend fun getAllCharacters(): List<RolCharacter>
    suspend fun getCharacterById(id: Int): RolCharacter?
    suspend fun insertCharacter(character: RolCharacter)
    suspend fun updateCharacter(character: RolCharacter)
    suspend fun deleteCharacter(character: RolCharacter)

    // RELACIONES CROSS-REF
    suspend fun getCharacterWithRelations(characterId: Int): RolCharacterWithAllRelations?
    suspend fun addItemToCharacter(character: RolCharacter, item: Item)

}