package com.unir.character.domain.usecase.skill

import com.unir.character.data.model.local.Skill
import com.unir.character.domain.repository.SkillRepository
import javax.inject.Inject

/**
 * Obtener lista de habilidades de la base de datos local. Utilizar SIEMPRE este caso de uso y NUNCA el FetchSKills.
 * */
class GetSkillsUseCase @Inject constructor(
    private val repository: SkillRepository
) {
    suspend operator fun invoke(): Result<List<Skill>> {
        return try {
            repository.getSkills()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
