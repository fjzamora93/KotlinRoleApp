package com.roleapp.character.domain.usecase.skill

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.Skill
import com.roleapp.character.data.model.local.SkillValue
import com.roleapp.character.domain.repository.SkillRepository
import com.roleapp.character.domain.usecase.character.generateutils.adjustCharacterSkills
import com.roleapp.character.domain.usecase.character.generateutils.calculateSkills
import com.roleapp.character.domain.usecase.character.generateutils.calculateTestResult
import com.roleapp.character.ui.screens.characterform.components.PersonalityTestForm
import javax.inject.Inject

class GenerateSkillValues @Inject constructor(private val repository: SkillRepository) {
    suspend operator fun invoke(
        character: CharacterEntity,
        form: PersonalityTestForm,
        skillList: List<Skill>
    ): Result<List<SkillValue>> {
        return try {

            // El primer paso simplemente calcula a las skills a partir de los stats
            var characterSkillCrossRef = calculateSkills(
                character = character,
                form = form,
                skillList = skillList
            )

            // El segundo paso es hacer los ajustes en función de la clase
            characterSkillCrossRef = adjustCharacterSkills(
                character = character,
                skillCrossRefList = characterSkillCrossRef
            )

            characterSkillCrossRef = calculateTestResult(
                form = form,
                originalCrossRef = characterSkillCrossRef
            )



            repository.generateSkills(characterSkillCrossRef, character.id)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}