package com.unir.sheet.data.model

import androidx.room.Embedded
import androidx.room.Relation


data class CharacterWithItems(
    @Embedded val character: CharacterEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val items: List<Item>
)


