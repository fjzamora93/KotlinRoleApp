package com.roleapp.character.domain.usecase.skill

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.SkillValue
import com.roleapp.character.domain.repository.SkillRepository
import javax.inject.Inject

class SaveSkillsUseCase @Inject constructor(private val repository: SkillRepository) {
    suspend operator fun invoke(
        character: CharacterEntity,
        skills: List<SkillValue>
    ): Result<Unit> {

        return try {
            repository.saveSkills(character.id, skills)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}