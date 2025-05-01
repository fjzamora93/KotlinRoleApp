package com.unir.roleapp.character.domain.repository

import com.unir.roleapp.character.data.model.local.CharacterEntity
import com.unir.roleapp.character.data.model.local.CharacterSkillCrossRef

interface CharacterRepository {
    suspend fun getCharactersByUserId(userId: Int): Result<List<CharacterEntity>>
    suspend fun getCharacterById(id: Long):  Result<CharacterEntity?>
    suspend fun saveCharacter(character: CharacterEntity): Result<CharacterEntity>
    suspend fun deleteCharacter(character: CharacterEntity): Result<Unit>
    suspend fun saveCharacterWithSKills(character: CharacterEntity, skillCrossRef: List<CharacterSkillCrossRef>) : Result<CharacterEntity>

    suspend fun getActiveCharacter(): Result<CharacterEntity>
}