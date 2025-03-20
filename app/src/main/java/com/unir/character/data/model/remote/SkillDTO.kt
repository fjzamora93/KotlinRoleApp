package com.unir.character.data.model.remote

import com.unir.character.data.model.local.Skill

data class SkillDTO(
    val id: Int,
    val name: String,
    val description: String
)

fun SkillDTO.toSkill(): Skill {
    return Skill(
        id = this.id,
        name = this.name,
        description = this.description
    )
}
