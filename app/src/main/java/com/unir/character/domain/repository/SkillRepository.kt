package com.unir.character.domain.repository

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.CharacterSkillCrossRef
import com.unir.character.data.model.local.Skill
import com.unir.character.data.model.local.SkillValue


interface SkillRepository {
    suspend fun getSkills(): Result<List<Skill>>
    suspend fun fetchSkillsFromApi(): Result<List<Skill>>
    suspend fun fetchCharacterSkillsFromApi(characterId: Long): Result<List<Skill>>
    suspend fun getSkillsFromCharacter(characterId: Long) : Result<List<SkillValue>>
    suspend fun saveSkills(characterId: Long, skills: List<SkillValue>): Result<Unit>

    suspend fun generateSkills(
        skillsCrossRef : List<CharacterSkillCrossRef>,
        characterId: Long
    ) : Result<List<SkillValue>>
}