package com.unir.sheet.domain.repository

import com.unir.sheet.data.local.dao.RolCharacterWithAllRelations
import com.unir.sheet.data.model.CharacterEntity

interface CharacterRepository {
    suspend fun getCharacterByUserId(userId: Int): Result<List<CharacterEntity>>
    suspend fun getCharacterById(id: Int):  Result<CharacterEntity?>
    suspend fun saveCharacter(character: CharacterEntity): Result<CharacterEntity>
    suspend fun deleteCharacter(character: CharacterEntity): Result<Unit>

    // ELIMINAR EN CUANTO TODOS LOS PERSONAJES TENGAN UN USER ID
    suspend fun getAllCharacters(): Result<List<CharacterEntity>>
}