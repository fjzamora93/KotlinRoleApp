package com.unir.character.domain.usecase.skill

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.CharacterSkillCrossRef
import com.unir.character.data.model.local.SkillValue
import com.unir.character.domain.repository.SkillRepository
import javax.inject.Inject

class GenerateSkillValues @Inject constructor(private val repository: SkillRepository) {
    suspend operator fun invoke(
        character: CharacterEntity,
    ): Result<List<SkillValue>> {
        return try {

            var habilidad1 = CharacterSkillCrossRef(
                characterId = character.id,
                skillId = 1,
                value = character.strength - 2
            )

            var habilidad2 = CharacterSkillCrossRef(
                characterId = character.id,
                skillId = 2,
                value = character.strength - 2
            )

            var habilidad3 = CharacterSkillCrossRef(
                characterId = character.id,
                skillId = 2,
                value = character.strength - 2
            )

            val skillList = listOf(habilidad1, habilidad2, habilidad3)
            repository.generateSkills(skillList, character.id)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}