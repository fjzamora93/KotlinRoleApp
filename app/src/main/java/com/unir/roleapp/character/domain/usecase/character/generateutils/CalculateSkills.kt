package com.roleapp.character.domain.usecase.character.generateutils

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.CharacterSkillCrossRef
import com.roleapp.character.data.model.local.Skill
import com.roleapp.character.ui.screens.characterform.components.PersonalityTestForm
import com.unir.roleapp.character.data.model.local.StatName

fun calculateSkills(
    form: PersonalityTestForm,
    character: CharacterEntity,
    skillList: List<Skill>
) : List<CharacterSkillCrossRef> {

    // Lista mutable para ir agregando las referencias cruzadas
    val skillCrossRefList = mutableListOf<CharacterSkillCrossRef>()

    // Iteramos sobre cada habilidad en la lista
    skillList.forEach { skill ->
        // Calculamos el valor según el tag de la habilidad
        var value : Int
        when (skill.tag) {
            StatName.STRENGTH -> value = character.strength - 3
            StatName.DEXTERITY-> value = character.dexterity - 3
            StatName.CONSTITUTION -> value = character.constitution - 3
            StatName.CHARISMA -> value = character.charisma - 3
            else -> value = 5
        }

        // Creamos el objeto CharacterSkillCrossRef y lo agregamos a la lista
        val skillCrossRef = CharacterSkillCrossRef(
            characterId = character.id,
            skillId = skill.id,
            value = value
        )

        // Agregamos la referencia cruzada a la lista
        skillCrossRefList.add(skillCrossRef)
    }



    // Devolvemos la lista con todas las referencias cruzadas
    return skillCrossRefList
}
