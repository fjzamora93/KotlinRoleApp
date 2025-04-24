package com.roleapp.character.data.model.remote

import com.roleapp.character.data.model.local.Skill
import com.unir.roleapp.character.data.model.local.StatName

data class SkillDTO(
    val id: Int,
    val name: String,
    val description: String,
    val tag: String // El nombre del Stat relacionado a la habilidad
)

fun SkillDTO.toSkill(): Skill {
    return Skill(
        id = this.id,
        name = this.name,
        description = this.description,
        tag = StatName.getStatName(this.tag)
    )
}
