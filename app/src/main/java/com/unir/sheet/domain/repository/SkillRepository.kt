package com.unir.sheet.domain.repository

import android.content.Context
import com.unir.sheet.data.model.Skill


interface SkillRepository {
    suspend fun getAllSkills() : Result<List<Skill>>
    suspend fun addSkillToCharacter(characterId: Int, skillId: Int) : Result<Unit>
    suspend fun deleteSkillFromCharacter(characterId: Int, skillId: Int) : Result<Unit>
    suspend fun getSkillsFromCharacter(characterId: Int) : Result<List<Skill>>
    suspend fun addDefaultSkills(characterId: Int, skillIds: List<Int>): Result<Unit>
}