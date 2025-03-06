package com.unir.sheet.domain.repository

import com.unir.sheet.data.model.CharacterEntity

interface CharacterRepository {
    suspend fun getCharactersByUserId(userId: Int): Result<List<CharacterEntity>>
    suspend fun getCharacterById(id: Int):  Result<CharacterEntity?>
    suspend fun saveCharacter(character: CharacterEntity): Result<CharacterEntity>
    suspend fun deleteCharacter(character: CharacterEntity): Result<Unit>


}