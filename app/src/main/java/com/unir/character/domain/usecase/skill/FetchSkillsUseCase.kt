package com.unir.character.domain.usecase.skill

import com.unir.character.data.model.local.Skill
import com.unir.character.domain.repository.SkillRepository
import javax.inject.Inject

/** NO UTILIZAR. SOLO EN EL INIT de SkillVIewModel.
 * Caso de uso para obtener las habilidades DESDE LA API. Utilizar solamente en el INIT del VIewModel. Para el resto, trabajar en local.
 */
class FetchSkillsUseCase @Inject constructor(
    private val repository: SkillRepository
) {
    suspend operator fun invoke(): Result<List<Skill>> {
        return try {
            repository.fetchSkillsFromApi()

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}