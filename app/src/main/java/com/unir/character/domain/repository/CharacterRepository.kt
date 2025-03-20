package com.unir.character.domain.repository

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.CharacterWithSkills

interface CharacterRepository {
    suspend fun getCharactersByUserId(userId: Int): Result<List<CharacterEntity>>
    suspend fun getCharacterById(id: Long):  Result<CharacterWithSkills?>
    suspend fun saveCharacter(character: CharacterEntity): Result<CharacterEntity>
    suspend fun deleteCharacter(character: CharacterEntity): Result<Unit>


}