package com.unir.character.domain.usecase.character.generateutils

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.CharacterSkillCrossRef
import com.unir.character.data.model.local.Skill
import com.unir.character.ui.screens.characterform.components.PersonalityTestForm


fun configureCharacterSkills(
    character: CharacterEntity,
    form: PersonalityTestForm,
    skillList : List<Skill>,
): List<CharacterSkillCrossRef> {

    // Comenzamos calculando las skills a partir de los stats
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

    // Modificamos las skills con el test de personalidad
    characterSkillCrossRef = calculateTestResult(
        form = form,
        originalCrossRef = characterSkillCrossRef
    )

    characterSkillCrossRef.forEach { skill ->
        if (skill.value < 5) {
            skill.value = 5
        }

        if (skill.value > 14) {
            skill.value = 14
        }
    }

    return characterSkillCrossRef
}