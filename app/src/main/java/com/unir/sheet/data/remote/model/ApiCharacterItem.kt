package com.unir.sheet.data.remote.model

import com.unir.sheet.data.model.CharacterItemDetail


data class ApiCharacterItem(
    val customItem: ApiItem,
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