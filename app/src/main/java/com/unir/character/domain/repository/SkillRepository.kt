package com.unir.character.domain.repository

import com.unir.character.data.model.local.Skill


interface SkillRepository {
    suspend fun getAllSkills() : Result<List<Skill>>
    suspend fun addSkillToCharacter(characterId: Long, skillId: Int) : Result<Unit>
    suspend fun deleteSkillFromCharacter(characterId: Long, skillId: Int) : Result<Unit>
    suspend fun getSkillsFromCharacter(characterId: Long) : Result<List<Skill>>
    suspend fun addDefaultSkills(characterId: Long, skillIds: List<Int>): Result<List<Skill>>
}