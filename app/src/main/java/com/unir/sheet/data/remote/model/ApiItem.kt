package com.unir.sheet.data.remote.model

import com.google.gson.annotations.SerializedName
import com.unir.sheet.data.model.Item

data class ApiItem(
    val id: Int,
    val characterId: Int?,
    val gameSession: Int?,
    val name: String,
    val description: String,
    val imgUrl: String?,
    val goldValue: Int,
    val category: String,
    val dice: Int,
    val quantity: Int,
    val statValue: Int,
    val statType: String
){
    /**
     * MAPEO INCOMPLETO, AÚN NO ASIGNA NI EL ID DEL PERSONAJE NI LA SESIÓN (CORREGIR EN LA API)
     * */
    fun toItemEntity(): Item {
        return Item(
            id = this.id ?: 0,
            characterId = this.characterId,
            gameSession = this.gameSession,
            name = this.name,
            description = this.description,
            imgUrl = this.imgUrl ?: "",
            goldValue = this.goldValue,
            category = this.category,
            dice = this.dice,
            quantity = this.quantity,
            statValue = this.statValue,
            statType = this.statType
        )
    }
}

