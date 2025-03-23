package com.unir.character.domain.usecase.skill

import com.unir.character.data.model.local.Skill
import com.unir.character.domain.repository.SkillRepository
import javax.inject.Inject

class FetchSkills @Inject constructor(private val repository: SkillRepository) {
    suspend operator fun invoke(): Result<List<Skill>> {
        return try {
            repository.fetchSkillsFromApi()

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}