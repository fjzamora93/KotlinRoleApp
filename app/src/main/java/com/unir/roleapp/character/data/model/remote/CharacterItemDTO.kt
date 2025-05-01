package com.unir.roleapp.character.data.model.remote

import com.unir.roleapp.character.data.model.local.CharacterItemDetail


data class ApiCharacterItem(
    val customItem: ItemDTO,
    val characterId: Long,
    val quantity: Int,
    val updatedAt: Long
)

fun ApiCharacterItem.toCharacterItemDetail(): CharacterItemDetail {
    return CharacterItemDetail(
        item = customItem.toItemEntity(),
        characterId = characterId,
        quantity = quantity,
        updatedAt = updatedAt

    )
}