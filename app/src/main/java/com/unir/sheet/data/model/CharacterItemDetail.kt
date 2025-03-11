package com.unir.sheet.data.model

import com.unir.sheet.data.remote.model.ApiCharacterItem


data class CharacterItemDetail(
    val item: Item,
    val characterId: Long,
    val quantity: Int,
    val updatedAt: Long
){
    fun toCharacterApiCharacterItem(): ApiCharacterItem {
        return ApiCharacterItem(
            customItem = this.item.toApiItem(),
            characterId = this.characterId,
            quantity = quantity,
            updatedAt = updatedAt

        )
    }
}

