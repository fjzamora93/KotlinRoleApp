package com.roleapp.character.domain.repository

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.CharacterSkillCrossRef
import com.unir.roleapp.character.data.model.remote.FirebaseCharacter

interface CharacterRepository {
    suspend fun getCharactersByUserId(userId: Int): Result<List<CharacterEntity>>
    suspend fun getCharacterById(id: Long):  Result<CharacterEntity?>
    suspend fun saveCharacter(character: CharacterEntity): Result<CharacterEntity>
    suspend fun deleteCharacter(character: CharacterEntity): Result<Unit>
    suspend fun saveCharacterWithSKills(character: CharacterEntity, skillCrossRef: List<CharacterSkillCrossRef>) : Result<CharacterEntity>

    suspend fun getActiveCharacter(): Result<CharacterEntity>
    suspend fun addCharacterToAdventure(character: FirebaseCharacter, sessionId : String): Result<CharacterEntity>

}