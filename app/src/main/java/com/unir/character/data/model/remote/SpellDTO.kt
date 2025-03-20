package com.unir.character.data.model.remote

import com.unir.character.data.model.local.Spell

data class SpellDTO(
    val id: Int,
    val name: String,
    val description: String,
    val dice: Int,
    val diceAmount: Int,
    val level: Int,
    val cost: Int,
    val imgUrl: String?
){

    // De la API no regresa ningún ID de personaje, así que debemos pasárselo como parámetro.
    fun toSpellEntity(): Spell {
        return Spell(
            id = this.id,
            name = this.name,
            description = this.description,
            dice = this.dice,
            diceAmount = this.diceAmount,
            level = this.level,
            cost = this.cost,
            imgUrl = this.imgUrl ?: ""
        )
    }


}

