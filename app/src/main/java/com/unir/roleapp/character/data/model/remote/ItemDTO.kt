package com.roleapp.character.data.model.remote

import com.roleapp.character.data.model.local.Item
import com.unir.roleapp.character.data.model.local.ItemCategory

data class ItemDTO(
    val id: Int,
    val name: String,
    val description: String,
    val imgUrl: String?,
    val goldValue: Int,
    val category: String,
    val dice: Int,
    val diceAmount: Int,
    val statValue: Int,
    val statType: String,
    val gameSession: Int?,

    ){
    /**
     * MAPEO INCOMPLETO, AÚN NO ASIGNA NI EL ID DEL PERSONAJE NI LA SESIÓN (CORREGIR EN LA API)
     * */
    fun toItemEntity(): Item {
        return Item(
            id = this.id ?: 0,
            name = this.name,
            description = this.description,
            imgUrl = this.imgUrl ?: "",
            goldValue = this.goldValue,
            category = ItemCategory.getItemCategory(this.category),
            dice = this.dice,
            statValue = this.statValue,
            statType = this.statType,
            diceAmount = this.diceAmount,
            gameSession = this.gameSession,

            )
    }
}

