package com.unir.sheet.domain.repository

import android.content.Context
import com.unir.sheet.data.model.Skill


interface SkillRepository {
    suspend fun getAllSkills() : Result<List<Skill>>
    suspend fun addSkillToCharacter(characterId: Long, skillId: Int) : Result<Unit>
    suspend fun deleteSkillFromCharacter(characterId: Long, skillId: Int) : Result<Unit>
    suspend fun getSkillsFromCharacter(characterId: Long) : Result<List<Skill>>
    suspend fun addDefaultSkills(characterId: Long, skillIds: List<Int>): Result<List<Skill>>
}