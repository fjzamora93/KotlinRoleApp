package com.unir.character.domain.usecase.character.generateutils

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.CharacterSkillCrossRef
import com.unir.character.data.model.local.Skill
import com.unir.character.data.model.local.SkillValue
import com.unir.character.ui.screens.skills.PersonalityTestForm

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
            "STR" -> value = character.strength - 2
            "DEX" -> value = character.dexterity - 2
            "INT" -> value = character.constitution - 2
            "CAR" -> value = character.charisma - 2
            else -> value = 7
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
