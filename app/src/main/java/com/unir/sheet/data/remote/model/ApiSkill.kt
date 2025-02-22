package com.unir.sheet.data.remote.model

import com.unir.sheet.data.model.Skill

data class ApiSkill(
    val id: Int,
    val name: String,
    val description: String
)

fun ApiSkill.toSkill(): Skill {
    return Skill(
        id = this.id,
        name = this.name,
        description = this.description
    )
}
