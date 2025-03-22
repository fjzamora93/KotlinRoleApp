package com.unir.character.domain.repository

import com.unir.character.data.model.local.CharacterEntity

interface CharacterRepository {
    suspend fun getCharactersByUserId(userId: Int): Result<List<CharacterEntity>>
    suspend fun getCharacterById(id: Long):  Result<CharacterEntity?>
    suspend fun saveCharacter(character: CharacterEntity): Result<CharacterEntity>
    suspend fun deleteCharacter(character: CharacterEntity): Result<Unit>


}