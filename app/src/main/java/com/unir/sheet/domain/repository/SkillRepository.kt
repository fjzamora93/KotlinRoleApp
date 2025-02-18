package com.unir.sheet.domain.repository

import android.content.Context
import com.unir.sheet.data.local.model.Skill


interface SkillRepository {
    suspend fun readFromJson(context: Context) : List<Skill>
    suspend fun fetchSkillsFromCharacter(characterId: Int, skillId: Int) : List<Skill>
}