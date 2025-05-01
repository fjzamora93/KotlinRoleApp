package com.unir.roleapp.character.data.model.remote

import com.unir.roleapp.character.data.model.local.Spell
import com.unir.roleapp.character.data.model.local.StatName

data class SpellDTO(
    val id: Int,
    val name: String,
    val description: String,
    val dice: Int,
    val dicesAmount: Int,
    val level: Int,
    val cost: Int,
    val imgUrl: String
){

    // De la API no regresa ningún ID de personaje, así que debemos pasárselo como parámetro.
    fun toSpellEntity(): Spell {
        return Spell(
            id = this.id,
            name = this.name,
            description = this.description,
            dice = this.dice,
            diceAmount = this.dicesAmount,
            level = this.level,
            cost = this.cost,
            imgUrl = StatName.getStatName(this.imgUrl)
        )
    }


}

