package com.unir.sheet.data.remote.model

import com.unir.sheet.data.model.Spell

data class ApiSpell(
    val id: Int,
    val name: String,
    val description: String,
    val dice: Int,
    val level: Int,
    val cost: Int,
    val imgUrl: String?
){
    fun toSpellEntity(): Spell {
        return Spell(
            id = this.id,
            name = this.name,
            description = this.description,
            dice = this.dice,
            level = this.level,
            cost = this.cost,
            imgUrl = this.imgUrl ?: ""
        )
    }


}

