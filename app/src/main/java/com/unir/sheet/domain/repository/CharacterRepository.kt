package com.unir.sheet.domain.repository

import com.unir.sheet.data.local.dao.RolCharacterWithAllRelations
import com.unir.sheet.data.model.CharacterEntity

interface CharacterRepository {
    suspend fun getAllCharacters(): List<CharacterEntity>
    suspend fun getCharacterByUserId(userId: Int): List<CharacterEntity>
    suspend fun getCharacterById(id: Int): CharacterEntity?
    suspend fun insertCharacter(character: CharacterEntity)
    suspend fun updateCharacter(character: CharacterEntity)
    suspend fun deleteCharacter(character: CharacterEntity)

    // RELACIONES CROSS-REF
    suspend fun getCharacterWithRelations(characterId: Int): RolCharacterWithAllRelations?

}