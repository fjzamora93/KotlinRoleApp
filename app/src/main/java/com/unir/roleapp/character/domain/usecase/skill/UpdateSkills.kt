package com.roleapp.character.domain.usecase.skill

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.SkillValue
import com.roleapp.character.domain.repository.SkillRepository
import javax.inject.Inject

class UpdateSkills @Inject constructor(private val repository: SkillRepository) {
    suspend operator fun invoke(
        character: CharacterEntity,
        skillList: List<SkillValue>
    ): Result<Unit> {
        return try {
            repository.saveSkills(character.id, skillList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}