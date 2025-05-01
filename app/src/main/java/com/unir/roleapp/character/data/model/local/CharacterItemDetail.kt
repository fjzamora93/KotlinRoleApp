package com.unir.roleapp.character.data.model.local

import com.unir.roleapp.character.data.model.remote.ApiCharacterItem


data class CharacterItemDetail(
    val item: Item,
    val characterId: Long,
    val quantity: Int,
    val updatedAt: Long
){
    fun toCharacterItemDTO(): ApiCharacterItem {
        return ApiCharacterItem(
            customItem = this.item.toApiItem(),
            characterId = this.characterId,
            quantity = quantity,
            updatedAt = updatedAt

        )
    }
}

